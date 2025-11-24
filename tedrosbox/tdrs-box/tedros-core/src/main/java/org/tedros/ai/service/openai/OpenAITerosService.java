package org.tedros.ai.service.openai;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.tedros.ai.function.TFunction;
import org.tedros.ai.openai.model.ToolCallResult;
import org.tedros.ai.service.AiServiceBase;
import org.tedros.ai.service.IAiTerosService;
import org.tedros.common.model.TFileContentInfo;
import org.tedros.core.TCoreKeys;
import org.tedros.core.TLanguage;
import org.tedros.core.context.TedrosContext;
import org.tedros.util.TDateUtil;
import org.tedros.util.TLoggerUtil;

import com.openai.models.files.FileCreateParams;
import com.openai.models.files.FileObject;
import com.openai.models.files.FilePurpose;
import com.openai.models.responses.EasyInputMessage;
import com.openai.models.responses.ResponseFunctionToolCall;
import com.openai.models.responses.ResponseInputContent;
import com.openai.models.responses.ResponseInputFile;
import com.openai.models.responses.ResponseInputItem;
import com.openai.models.responses.ResponseOutputItem;
import com.openai.models.responses.ResponseOutputMessage;
import com.openai.models.responses.ResponseOutputMessage.Content;
import com.openai.models.responses.ResponseOutputRefusal;
import com.openai.models.responses.ResponseOutputText;
import com.openai.models.responses.ResponseReasoningItem;
import com.openai.models.responses.ResponseReasoningItem.Summary;

import javafx.application.Platform;

/**
 * Versão adaptada do TerosService usando o SDK oficial da openai
 */
public class OpenAITerosService extends AiServiceBase implements IAiTerosService {
    
	static final Logger log = TLoggerUtil.getLogger(OpenAITerosService.class);
    
	private static IAiTerosService instance;
	
	private static final Predicate<ResponseInputItem> IS_USER_MESSAGE = item ->
    	(item.isMessage() && item.asMessage().role() == ResponseInputItem.Message.Role.USER) ||
    	(item.isEasyInputMessage() && item.asEasyInputMessage().role() == EasyInputMessage.Role.USER);
    
    private final OpenAiServiceAdapter adapter;
    private final List<ResponseInputItem> messages = new ArrayList<>();
    
    private OpenAIFunctionExecutor functionExecutor;
    
    private OpenAITerosService(String token, String aiModel, String assistantPrompt) {
		this.adapter = new OpenAiServiceAdapter(token, aiModel);
		setPromptAssistant(assistantPrompt);
        createSystemMessage();
        log.info("OpenAI Teros Service iniciado com sucesso. "
        		+ "Modelo padrão: {}", aiModel != null ? aiModel : "não definido");
    }

    public static IAiTerosService create(String token, String aiModel, String assistantPrompt) {
    	if (instance == null)
            instance = new OpenAITerosService(token, aiModel, assistantPrompt);
        return instance;
    }

    @Override
	public void createFunctionExecutor(TFunction<?>... functions) {
    	this.adapter.functions(Arrays.asList(functions));
    	this.functionExecutor = new OpenAIFunctionExecutor(functions);
    	log.info("Registradas {} função(ões) personalizada(s) para tool calls.", functions.length);
    }
    
    @Override
	public String call(String userPrompt, String sysPrompt) {
    	
    	log.debug(">>> Iniciando nova interação com Teros");
        log.trace("Prompt do usuário: {}", userPrompt);
        
        if (sysPrompt != null && !sysPrompt.isBlank()) {
            log.trace("Prompt de sistema adicional: {}", sysPrompt);
            messages.add(adapter.buildSysMessage(sysPrompt));
        }

        messages.add(adapter.buildUserMessage(userPrompt));

        long startTime = System.currentTimeMillis();
        List<ResponseOutputItem> response = adapter.sendChatRequest(messages);
        long elapsed = System.currentTimeMillis() - startTime;
        
        log.info("Resposta da OpenAI recebida em {}ms | {} itens | Tokens de entrada: {} | Uso total estimado: {}",
                elapsed,
                response.size(),
                adapter.totalInputTokenProperty().get(),
                adapter.getLastUsage()!=null ? adapter.getLastUsage().totalTokens() : "?");

        String output = processAiResponseMessage(response);
        
        // Verifica se precisa resumir com base no modelo atual
        long currentTokens = adapter.totalInputTokenProperty().longValue();
        long threshold = getDynamicSummarizationThreshold();

        log.info("Tokens atuais: {} | Threshold ({}% de contexto do modelo {}): {}",
                currentTokens,
                (int)(SUMMARIZATION_THRESHOLD_PERCENT * 100),
                getAiModel(),
                threshold);

        if (currentTokens > threshold) {
        	log.warn("Threshold de tokens excedido ({} > {}). Iniciando sumarização automática...", currentTokens, threshold);
            summarizeMessages();
        }

        log.debug("<<< Interação concluída. Resposta final tem {} caracteres.", output.length());
        return output.isEmpty() ? NO_RESPONSE : output;
    }
    
    @Override
	public void setAiModel(String model) {
	    adapter.setAiModel(model);
	    log.info("Modelo GPT definido: {}", model);
	}
    
    @Override
    public String getAiModel() {
    	return adapter.getAiModel();
    }

    private String processAiResponseMessage(List<ResponseOutputItem> responseItems) {

    	if (responseItems == null || responseItems.isEmpty()) {
            log.warn("Resposta da OpenAI veio vazia ou nula.");
            return NO_RESPONSE;
        }
    	
        StringBuilder finalContent = new StringBuilder();
        
        ResponseReasoningItem lastResponseReasoningItem = null;
        
        for (ResponseOutputItem item : responseItems) {
            if (!item.isValid()) {
                log.warn("Item inválido na resposta.");
                continue;
            }

            if (item.isMessage()) {
                // Process text message
                processTextMessageResponse(finalContent, item);
            }
            
            else if (item.isReasoning()) {
            	// Process reasoning message 
            	lastResponseReasoningItem = processReasoningResponse(item);
            }

            else if (item.isFunctionCall()) {
            	// Process function call message
            	log.info("Detectado tool call: {} (id={})", item.asFunctionCall().name(), item.asFunctionCall().callId());
            	processFunctionCallResponse(finalContent, lastResponseReasoningItem, item);
            }
        }

        String result = finalContent.toString().trim();
        return result.isEmpty() ? NO_RESPONSE : result;
    }
    
    private void summarizeMessages() {
        try {
        	log.info("Iniciando processo de sumarização do histórico ({} mensagens atuais)", messages.size());

            // 1. Criar instrução de resumo
            ResponseInputItem sysSummaryInstruction = adapter.buildSysMessage(
                "Summarize the previous conversation as concisely as possible. " +
                "Preserve important context, decisions made, and unresolved tasks. " +
                "Do NOT include token usage stats or meta-information. " +
                "Your output MUST be only the summary text."
            );

            List<ResponseInputItem> tempMessages = new ArrayList<>();
            tempMessages.add(sysSummaryInstruction);
            tempMessages.addAll(messages);

            // 2. Fazer requisição ao modelo para gerar o resumo
            String summary = adapter.sendChatRequest(tempMessages).stream()
            	    .filter(ResponseOutputItem::isMessage)   // só itens que são mensagens
            	    .map(ResponseOutputItem::message)        // Optional<ResponseOutputMessage>
            	    .flatMap(Optional::stream)               // transforma Optional em Stream (0 ou 1 elemento)
            	    .flatMap(msg -> msg.content().stream())  // todos os Content da mensagem
            	    .filter(Content::isOutputText)           // só conteúdos de texto
            	    .map(Content::outputText)                // Optional<ResponseOutputText>
            	    .flatMap(Optional::stream)               // novamente para lidar com o Optional
            	    .map(ResponseOutputText::text)           // pega o texto
            	    .collect(Collectors.joining("\n"));      // junta tudo com quebra de linha

            if (summary == null || summary.isBlank()) {
                log.warn("Sumarização retornou vazio. Abortando substituição do histórico.");
                return;
            }

            log.info("Sumarização gerada com {} caracteres.", summary.length());

            // 3. Manter apenas:
            // - Mensagem SYSTEM original
            // - Resumo
            // - Última mensagem USER (para manter continuidade)
            ResponseInputItem originalSystemMessage = messages.get(0);

            ResponseInputItem lastUserMessage = Stream.iterate(messages.size() - 1, i -> i >= 0, i -> i - 1)
                .map(messages::get)
                .filter(IS_USER_MESSAGE)
                .findFirst()
                .orElse(null);

            List<ResponseInputItem> newMessages = new ArrayList<>();
            newMessages.add(originalSystemMessage);

            // inserir o resumo como SYSTEM
            newMessages.add(adapter.buildSysMessage("Summary of earlier conversation:\n" + summary));

            if (lastUserMessage != null) {
                newMessages.add(lastUserMessage);
            }

            // 4. Substituir histórico de mensagens
            messages.clear();
            messages.addAll(newMessages);

            log.info("Histórico resumido com sucesso → {} mensagens restantes.", messages.size());

        } catch (Exception e) {
        	log.error("Falha crítica durante sumarização do contexto", e);
        }
    }
	
    private void processFunctionCallResponse(
            StringBuilder finalContent,
            ResponseReasoningItem lastResponseReasoningItem,
            ResponseOutputItem item) {

        ResponseFunctionToolCall toolCall = item.asFunctionCall();
        String callId = toolCall.callId();
        String funcName = toolCall.name();

        log.info("Executando tool call → {} (call_id={})", funcName, callId);
        
        
        Optional<ToolCallResult> resultOpt = functionExecutor.callFunction(toolCall);
        
        if (resultOpt.isEmpty()) {
        	log.error("Função '{}' não registrada! Ignorando tool call {}", funcName, callId);
            return;
        }

        ToolCallResult result = resultOpt.get();
        List<String> uploadedFileIds = new ArrayList<>(); // Para deletar depois

        try {
            // 1. Adiciona chamada da função e resultado (texto)
            ResponseInputItem functionCallInput = ResponseInputItem.ofFunctionCall(toolCall);
            ResponseInputItem functionCallOutput = ResponseInputItem.ofFunctionCallOutput(
                ResponseInputItem.FunctionCallOutput.builder()
                    .callId(toolCall.callId())
                    .output(mapper.writeValueAsString(result.getResult()))
                    .build()
            );

            // Payload temporário para enviar ao modelo
            List<ResponseInputItem> toolRequest = new ArrayList<>();

            if (lastResponseReasoningItem != null) {
                toolRequest.add(ResponseInputItem.ofReasoning(lastResponseReasoningItem));
            }

            toolRequest.add(functionCallInput);
            toolRequest.add(functionCallOutput);

            // 2. Processa arquivos retornados pela função (upload + file_id)
            if (result.getFilesContentInfo() != null && !result.getFilesContentInfo().isEmpty()) {
            	log.info("Tool call retornou {} arquivo(s). Fazendo upload temporário...", result.getFilesContentInfo().size());
                StringBuilder fileInfoText = new StringBuilder();
                fileInfoText.append("The function call (id: ").append(toolCall.callId())
                            .append(") returned the following file(s) for analysis:\n");

                for (TFileContentInfo fileContentInfo : result.getFilesContentInfo()) {
                    uploadFile(uploadedFileIds, toolRequest, fileInfoText, fileContentInfo);
                }

                // Adiciona uma mensagem de resumo dos arquivos anexados
                toolRequest.add(ResponseInputItem.ofMessage(
                    ResponseInputItem.Message.builder()
                        .role(ResponseInputItem.Message.Role.SYSTEM)
                        .addInputTextContent(fileInfoText.toString().trim())
                        .build()
                ));
            }

            // 3. Envia tudo de volta ao modelo
            List<ResponseOutputItem> nextResponse = adapter.sendToolCallResult(toolRequest);

            // Processa resposta recursivamente
            String recursiveContent = processAiResponseMessage(nextResponse);
            if (recursiveContent != null && !recursiveContent.equals(NO_RESPONSE)) {
                finalContent.append(recursiveContent);
            }
            
            log.info("Tool call {} concluído com sucesso.", callId);

        } catch (Exception e) {
        	log.error("Erro inesperado ao processar tool call {}", callId, e);
            finalContent.append("\n[Erro interno ao processar função. Tente novamente.]");
        } finally {
            // SEMPRE deleta os arquivos temporários, mesmo em caso de erro
            uploadedFileIds.forEach(fileId -> {
                try {
                    adapter.getClient().files().delete(fileId);
                    log.debug("Arquivo temporário deletado: {}", fileId);
                } catch (Exception e) {
                	log.warn("Falha ao deletar arquivo temporário {}: {}", fileId, e.toString());
                }
            });
        }
    }

	private void uploadFile(List<String> uploadedFileIds, List<ResponseInputItem> toolRequest,
			StringBuilder fileInfoText, TFileContentInfo fileContentInfo) {
		try {
		    // Upload do arquivo		    
			try(ByteArrayInputStream bais = new ByteArrayInputStream(fileContentInfo.bytes())){
				FileCreateParams uploadParams = FileCreateParams.builder()
				        .file(bais)
				        .purpose(FilePurpose.ASSISTANTS) // ou VISION se for imagem
				        .build();

				    FileObject uploadedFile = adapter.getClient().files().create(uploadParams);
				    String fileId = uploadedFile.id();
				    uploadedFileIds.add(fileId); // Marca para deleção

				    fileInfoText.append("- ").append(fileContentInfo.fileName())
				                .append(" (file_id: ").append(fileId).append(")\n");

				    // Adiciona referência ao arquivo como content (suportado no Responses API)
				    ResponseInputItem fileRefItem = ResponseInputItem.ofMessage(
				        ResponseInputItem.Message.builder()
				            .role(ResponseInputItem.Message.Role.SYSTEM)
				            .addInputTextContent("Attached file: " + fileContentInfo.fileName() + " (file_id: " + fileId + ")")
				            .addContent(ResponseInputContent.ofInputFile(
				            				ResponseInputFile.builder()
				            				.fileId(fileId)
				            				.build()))
				            .build()
				    );
				    toolRequest.add(fileRefItem);
			}
			
			log.debug("Upload temporário concluído → {} ({} bytes)", fileContentInfo.fileName(), fileContentInfo.bytes().length);

		} catch (Exception e) {
			log.error("Falha no upload do arquivo retornado pela função: {}", fileContentInfo.fileName(), e);
		    fileInfoText.append("- [ERRO] Falha ao anexar: ").append(fileContentInfo.fileName()).append("\n");
		}
	}

	
	private ResponseReasoningItem processReasoningResponse(ResponseOutputItem item) {
		ResponseReasoningItem lastResponseReasoningItem;
		Platform.runLater(()-> {
			ResponseReasoningItem reasoning = item.asReasoning();
			
			List<String> summaryList = reasoning.summary().stream()
					.map(Summary::text)
					.toList();
			
			if(!summaryList.isEmpty()) {
				reasoningsMessageProperty.addAll(summaryList);
			}else {
				reasoningsMessageProperty.add(TLanguage.getInstance().getString(TCoreKeys.AI_THINKING));
			}	
		});
		
		lastResponseReasoningItem = item.asReasoning();
		log.info("Reasoning recebido {} ", lastResponseReasoningItem);
		return lastResponseReasoningItem;
	}

	private void processTextMessageResponse(StringBuilder finalContent, ResponseOutputItem item) {
		Optional<ResponseOutputMessage> msgOpt = item.message();
		if (msgOpt.isPresent()) {
		    ResponseOutputMessage msg = msgOpt.get();
		    for (Content content : msg.content()) {
		        if (content.isOutputText() && content.outputText().isPresent()) {		            
		        	Optional<ResponseOutputText> opt = content.outputText();
		        	if(opt.isPresent()) {
		        		String text = opt.get().text();
			            finalContent.append(text).append("\n");
			            messages.add(adapter.buildAssistantMessage(text));
			            log.trace("Texto do assistente adicionado ({} chars)", text.length());
		        	}		            
		        } else if (content.isRefusal() && content.refusal().isPresent()) {
		        	Optional<ResponseOutputRefusal> opt = content.refusal();
		        	if(opt.isPresent()) {
		        		String refusal = opt.get().refusal();
		        		log.warn("Modelo recusou gerar conteúdo: {}", refusal);
			            finalContent.append("Recusa: ").append(refusal);
		        	}
		        }
		    }
		}
	}

	private void createSystemMessage() {    	
        String date = TDateUtil.formatFullgDate(new Date(), TLanguage.getLocale());
        String user = TedrosContext.getLoggedUser().getName();        
        String header = "Today is %s. You are Teros, a smart and helpful assistant for the "
        		+ "Tedros desktop system. Engage intelligently with user %s.".formatted(date, user);
        
        if (assistantPrompt != null)
            header += " " + assistantPrompt;

        messages.add(adapter.buildSysMessage(header));
        log.info("Mensagem de sistema inicial criada para usuário '{}'", user);
    }
}
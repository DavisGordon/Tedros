package org.tedros.ai.service.openai.reasoning;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
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
import org.tedros.ai.service.openai.OpenAIFunctionExecutor;
import org.tedros.common.model.TFileContentInfo;
import org.tedros.core.TCoreKeys;
import org.tedros.core.TLanguage;
import org.tedros.core.context.TedrosContext;
import org.tedros.util.TDateUtil;
import org.tedros.util.TLoggerUtil;

import com.openai.core.MultipartField;
import com.openai.models.files.FileCreateParams;
import com.openai.models.files.FileObject;
import com.openai.models.files.FilePurpose;
import com.openai.models.responses.EasyInputMessage;
import com.openai.models.responses.ResponseFunctionToolCall;
import com.openai.models.responses.ResponseInputFile;
import com.openai.models.responses.ResponseInputItem;
import com.openai.models.responses.ResponseInputText;
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
public class OpenAIReasoningTerosService extends AiServiceBase implements IAiTerosService {
    
	private static final Logger log = TLoggerUtil.getLogger(OpenAIReasoningTerosService.class);
    
	private static IAiTerosService instance;
	
	private static final Predicate<ResponseInputItem> IS_USER_MESSAGE = item ->
    	(item.isMessage() && item.asMessage().role() == ResponseInputItem.Message.Role.USER) ||
    	(item.isEasyInputMessage() && item.asEasyInputMessage().role() == EasyInputMessage.Role.USER);
    
    private final OpenAiReasoningServiceAdapter adapter;
    private final List<ResponseInputItem> messages = new ArrayList<>();
    
    private String lastUserMessage;
    private OpenAIFunctionExecutor functionExecutor;
    
    private OpenAIReasoningTerosService(String token, String aiModel, String assistantPrompt) {
    	
    	String date = TDateUtil.formatFullgDate(new Date(), TLanguage.getLocale());
        String promptComplement = """
        		\n        		
        		==================================================
        		SYSTEM METADATA
        		==================================================
        		- Current date: %s
        		""".formatted(date);
    	
        setPromptAssistant(assistantPrompt + promptComplement);
        
    	this.adapter = new OpenAiReasoningServiceAdapter(token, aiModel, super.assistantPrompt);
    	
    	String userNamePrompt = "The logged-in user is named %s".formatted(TedrosContext.getLoggedUser().getName());
    	
    	messages.add(ResponseInputItem.ofEasyInputMessage(EasyInputMessage.builder()
    			.role(EasyInputMessage.Role.SYSTEM)
    			.content(userNamePrompt).build()));
		
        log.info("OpenAI Teros Service iniciado com sucesso. Modelo padrão: {}", aiModel != null ? aiModel : "não definido");
    }

    public static IAiTerosService create(String token, String aiModel, String assistantPrompt) {
    	if (instance == null)
            instance = new OpenAIReasoningTerosService(token, aiModel, assistantPrompt);
        return instance;
    }
    
    public static IAiTerosService newInstance(String token, String aiModel, String assistantPrompt) {
    	return new OpenAIReasoningTerosService(token, aiModel, assistantPrompt);
    }
    
    public static IAiTerosService getInstance() {
        if (instance == null) throw new IllegalStateException("Instância não criada!");
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
    	
    	log.info(">>> Iniciando nova interação com Teros");
        log.info("Prompt do usuário: {}", userPrompt);
        
        lastUserMessage = userPrompt;
        
        if (sysPrompt != null && !sysPrompt.isBlank()) {
            log.info("Prompt de sistema adicional: {}", sysPrompt);
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

        String output = processAiResponseMessage(response, 0);
        
        // Verifica se precisa resumir com base no modelo atual
        long currentTokens = adapter.totalInputTokenProperty().longValue();
        long threshold = getDynamicSummarizationThreshold();

        log.info("Tokens atuais: {} | Threshold ({}% de contexto do modelo {}): {}",
                currentTokens,
                (int)(SUMMARIZATION_THRESHOLD_PERCENT * 100),
                getAiModel(),
                threshold);

        if (currentTokens > threshold) {
        	log.info("Threshold de tokens excedido ({} > {}). Iniciando sumarização automática...", currentTokens, threshold);
            summarizeMessages();
        }
        
        if(!output.isEmpty() && output.contains(EMPTY_TOOL_CALL_RESPONSE)) {
        	output = output.replaceAll(EMPTY_TOOL_CALL_RESPONSE, "");
        	log.info("<<< Interação concluída. Resposta final tem {} caracteres.", output.length());
        	return output;
        }

        log.info("<<< Interação concluída. Resposta final tem {} caracteres.", output.length());
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

    private String processAiResponseMessage(List<ResponseOutputItem> responseItems, int currentDepth) {

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
            	processFunctionCallResponse(finalContent, lastResponseReasoningItem, item, currentDepth);
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
            // - Resumo
            // - Última mensagem USER (para manter continuidade)

            ResponseInputItem lastUserMessage = Stream.iterate(messages.size() - 1, i -> i >= 0, i -> i - 1)
        		    .map(messages::get)
        		    .filter(IS_USER_MESSAGE)
        		    .findFirst()
        		    .orElse(null);
            
            List<ResponseInputItem> newMessages = new ArrayList<>();
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
            ResponseOutputItem item, int currentDepth) {
    	
    	// TRAVA DE SEGURANÇA
        if (currentDepth >= MAX_RECURSION_DEPTH) {
            log.warn("Limite de recursão de Tool Calls atingido ({})", MAX_RECURSION_DEPTH);
            return; 
        }

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
        log.info("Resultado da função {} : {}", funcName, result);
        
        if(!result.isRevertToTheAIModelInCaseOfSuccess()) {
        	if(finalContent.isEmpty())
        		finalContent.append(EMPTY_TOOL_CALL_RESPONSE);
        	return;
        }
        
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
                
                for (TFileContentInfo fileContentInfo : result.getFilesContentInfo()) {
                    uploadFile(uploadedFileIds, toolRequest, fileContentInfo);
                }                
            }

            // 3. Envia tudo de volta ao modelo
            List<ResponseOutputItem> nextResponse = adapter.sendToolCallResult(toolRequest);

            // Processa resposta recursivamente
            String recursiveContent = processAiResponseMessage(nextResponse, currentDepth + 1);
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
			TFileContentInfo fileContentInfo) {
		try {
		    // Upload do arquivo		    
			try(ByteArrayInputStream bais = new ByteArrayInputStream(fileContentInfo.bytes())){
				FileCreateParams uploadParams = FileCreateParams.builder()
				        //.file(bais)
						.file(MultipartField.<InputStream>builder()
						        .value(bais)
						        .filename(fileContentInfo.fileName())
						        .build())
				        .purpose(FilePurpose.USER_DATA)
				        .build();

				    FileObject uploadedFile = adapter.getClient().files().create(uploadParams);
				    String fileId = uploadedFile.id();
				    
				    uploadedFileIds.add(fileId); // Marca para deleção
				    
				    log.info("Arquivo '{}' carregado com sucesso → file_id={}", 
				    	fileContentInfo.fileName(), fileId);
				    
				    // Adiciona referência ao arquivo como content (suportado no Responses API)
				    ResponseInputItem fileRefItem = ResponseInputItem.ofMessage(
				        ResponseInputItem.Message.builder()
				            .role(ResponseInputItem.Message.Role.USER)
				            .addContent(ResponseInputText.builder().text(lastUserMessage)
				            		.build())
				            .addContent(ResponseInputFile.builder()
				            				.fileId(fileId)
				            				.build())				            
				            .build()
				    );
				    
				    toolRequest.add(fileRefItem);
			}
			
			log.debug("Upload temporário concluído → {} ({} bytes)", fileContentInfo.fileName(), fileContentInfo.bytes().length);

		} catch (Exception e) {
			log.error("Falha no upload do arquivo retornado pela função: {}", fileContentInfo.fileName(), e);
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

	@Override
	public void cleanMessageHistory() {
		this.messages.clear();
		this.adapter.resetBuilder();
	}
}
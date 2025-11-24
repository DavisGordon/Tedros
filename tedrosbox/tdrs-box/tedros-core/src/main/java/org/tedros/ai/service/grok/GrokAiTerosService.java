package org.tedros.ai.service.grok;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.slf4j.Logger;
import org.tedros.ai.openai.model.ToolCallResult;
import org.tedros.ai.service.AiServiceBase;
import org.tedros.ai.service.DocumentConverter;
import org.tedros.ai.service.IAiTerosService;
import org.tedros.common.model.TFileContentInfo;
import org.tedros.core.context.TedrosContext;
import org.tedros.util.TDateUtil;
import org.tedros.util.TLoggerUtil;

import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionAssistantMessageParam;
import com.openai.models.chat.completions.ChatCompletionContentPart;
import com.openai.models.chat.completions.ChatCompletionContentPartImage;
import com.openai.models.chat.completions.ChatCompletionContentPartText;
import com.openai.models.chat.completions.ChatCompletionMessage;
import com.openai.models.chat.completions.ChatCompletionMessageFunctionToolCall;
import com.openai.models.chat.completions.ChatCompletionMessageParam;
import com.openai.models.chat.completions.ChatCompletionMessageToolCall;
import com.openai.models.chat.completions.ChatCompletionSystemMessageParam;
import com.openai.models.chat.completions.ChatCompletionToolMessageParam;
import com.openai.models.chat.completions.ChatCompletionUserMessageParam;
import com.openai.models.files.FileObject;

public class GrokAiTerosService extends AiServiceBase implements IAiTerosService {

    private static final Logger log = TLoggerUtil.getLogger(GrokAiTerosService.class);
    
    private static GrokAiTerosService instance;
    private final GrokAiServiceAdapter adapter;
    
    private final List<ChatCompletionMessageParam> messages = new ArrayList<>();
    private final List<String> uploadedFileIds = new ArrayList<>();
    private GrokAiFunctionExecutor functionExecutor;

    private GrokAiTerosService(String apiKey, String aiModel, String assistantPrompt) {
        this.adapter = new GrokAiServiceAdapter(apiKey, aiModel);
        setPromptAssistant(assistantPrompt);
        createSystemMessage();
    }

    public static GrokAiTerosService create(String apiKey, String aiModel, String assistantPrompt) {
        if (instance == null) instance = new GrokAiTerosService(apiKey, aiModel, assistantPrompt);
        return instance;
    }

    public static GrokAiTerosService getInstance() {
        if (instance == null) throw new IllegalStateException("Instância não criada!");
        return instance;
    }

    public void createFunctionExecutor(org.tedros.ai.function.TFunction<?>... functions) {
        this.adapter.functions(Arrays.asList(functions));
        this.functionExecutor = new GrokAiFunctionExecutor(functions);
        log.info("Registradas {} função(ões) para tool calls no Grok.", functions.length);
    }

    @Override
    public void setAiModel(String model) {
        adapter.setAiModel(model);
        log.info("Modelo Grok definido: {}", model);
    }

    @Override
    public String getAiModel() {
    	return adapter.getAiModel();
    }
    
    public String call(String userPrompt, String sysPrompt) {
        if (sysPrompt != null && !sysPrompt.isBlank()) {
            messages.add(ChatCompletionMessageParam.ofSystem(ChatCompletionSystemMessageParam.builder().content(sysPrompt).build()));
        }
        messages.add(ChatCompletionMessageParam.ofUser(ChatCompletionUserMessageParam.builder().content(userPrompt).build()));

        String result = processResponse(adapter.sendChatRequest(messages), 0);

        checkAndSummarize();
        removeUploadedFiles();
        
        return result.isEmpty() ? NO_RESPONSE : result;
    }

    private String processResponse(ChatCompletion response, int currentDepth) {
    	StringBuilder finalContent = new StringBuilder();
        var choices = response.choices();

        for (var choice : choices) {
            ChatCompletionMessage message = choice.message();
            Optional<String> contentOpt = message.content();
            if (contentOpt.isPresent()) {
                String content = contentOpt.get();
                if (!content.isBlank()) {
                    finalContent.append(content);
                    messages.add(ChatCompletionMessageParam.ofAssistant(
							ChatCompletionAssistantMessageParam.builder().content(content).build()));
                }
            }
            
            // Processa tool calls
	        message.toolCalls().ifPresent(toolCalls -> {
	            for (ChatCompletionMessageToolCall toolCall : toolCalls) {
	                processToolCall(toolCall, finalContent, currentDepth);
	            }
	        });
        }
        return finalContent.toString().trim();
    }

    private void processToolCall(ChatCompletionMessageToolCall messageToolCall, StringBuilder output, int currentDepth) {
    	
    	// TRAVA DE SEGURANÇA AQUI
        if (currentDepth >= MAX_RECURSION_DEPTH) {
            log.warn("Limite de recursão de Tool Calls atingido ({})", MAX_RECURSION_DEPTH);
            return; 
        }
    	
    	
    	Optional<ChatCompletionMessageFunctionToolCall> functionToolCallOpt = messageToolCall.function();
    	ChatCompletionMessageFunctionToolCall toolCall = functionToolCallOpt.get();
        log.info("Tool call detectada: {} (id={})", toolCall.function().name(), toolCall.id());

        Optional<ToolCallResult> resultOpt = functionExecutor.callFunction(toolCall);
        if (resultOpt.isEmpty()) {
        	log.info("Função não encontrada: {} (id={})", toolCall.function().name(), toolCall.id());
            output.append("\n[Função não encontrada: ").append(toolCall.function().name()).append("]");
            return;
        }

        ToolCallResult result = resultOpt.get();
        

        try {
        	if(result.getResult() != null) {
        		String resultJson = mapper.writeValueAsString(result.getResult());
	            messages.add(ChatCompletionMessageParam.ofTool(ChatCompletionToolMessageParam.builder()
	            		.toolCallId(toolCall.id())
	            		.contentAsJson(resultJson)
	            		.build()));
	            log.info("Resultado da função {} adicionado no historico de mensagens", toolCall.function().name());
            }

            // Upload de arquivos retornados
            if ((result.getFilesContentInfo() != null && !result.getFilesContentInfo().isEmpty())
            		
            		) {
                
            	// Lista para montar a mensagem multimodal do USUÁRIO
                List<ChatCompletionContentPart> contentParts = new ArrayList<>();
                
                // Texto introdutório
                contentParts.add(ChatCompletionContentPart.ofText(
                    ChatCompletionContentPartText.builder()
                    .text("Arquivos processados pelo sistema. Segue análise de conteúdo:")
                    .build()
                ));
                
                for (TFileContentInfo fileInfo : result.getFilesContentInfo()) {
                    
                    // 1. SEMPRE faz o upload do binário para a API (Garantia de Fallback)
                    FileObject uploaded = adapter.uploadFile(fileInfo.bytes(), fileInfo.fileName());
                    uploadedFileIds.add(uploaded.id());
                    log.info("Upload realizado: {} (ID: {})", fileInfo.fileName(), uploaded.id());

                    // 2. Tenta extrair conteúdo localmente (Texto e/ou Imagem)
                    var processed = DocumentConverter.processFile(fileInfo.bytes(), fileInfo.fileName());

                    // 3. Adiciona Cabeçalho com ID do Upload
                    String header = String.format("\n=== ARQUIVO: %s (ID Remoto: %s) ===\n", 
                                                  fileInfo.fileName(), uploaded.id());
                    
                    contentParts.add(ChatCompletionContentPart.ofText(
                         ChatCompletionContentPartText.builder().text(header).build()
                    ));

                    // 4. Se tiver texto extraído, adiciona
                    if (processed.textContent() != null && !processed.textContent().isBlank()) {
                        contentParts.add(ChatCompletionContentPart.ofText(
                            ChatCompletionContentPartText.builder()
                            .text("Conteúdo Textual:\n" + processed.textContent())
                            .build()
                        ));
                    }

                    // 5. Se tiver imagens (PDF renderizado ou JPG/PNG nativo), adiciona
                    for (String dataUrl : processed.base64Images()) {
                        contentParts.add(ChatCompletionContentPart.ofImageUrl(
                            ChatCompletionContentPartImage.builder()
                                .imageUrl(ChatCompletionContentPartImage.ImageUrl.builder()
                                    .url(dataUrl) // Agora o DocumentConverter já devolve o "data:image..." completo
                                    .detail(ChatCompletionContentPartImage.ImageUrl.Detail.LOW)
                                    .build())
                                .build()
                        ));
                    }
                }
            	
                
             // Envia tudo como uma mensagem de User (pois contém imagens)
                messages.add(ChatCompletionMessageParam.ofUser(
                    ChatCompletionUserMessageParam.builder()
                        .contentOfArrayOfContentParts(contentParts) 
                        .build()
                ));
                
                log.info("Contexto multimodal injetado.");
                
            	/*for (TFileContentInfo fileInfo : result.getFilesContentInfo()) {
            		
            		FileObject uploaded = adapter.uploadFile(fileInfo.bytes(), fileInfo.fileName());
                    log.info("Upload do arquivo {} realizado para na função: {} (id={})", 
                    		fileInfo.fileName(), toolCall.function().name(), toolCall.id());
                    
                    uploadedFileIds.add(uploaded.id());
                    
                    messages.add(ChatCompletionMessageParam.ofSystem(ChatCompletionSystemMessageParam.builder()
                    		.content("Arquivo anexado: " + fileInfo.fileName() + " (file_id: " + uploaded.id() + ")")
                    		.build()));
                    log.info("Dados do arquivo {} adicionado no historico da mensagem para na função: {} (id={})", 
                    		fileInfo.fileName(), toolCall.function().name(), toolCall.id());
                }*/
            }

            // Nova chamada recursiva
            log.info("Enviando historico de mensagens para o modelo de IA: {} (id={})", 
            		toolCall.function().name(), toolCall.id());
            
            String recursive = processResponse(adapter.sendChatRequest(messages), currentDepth + 1);
            
            if (!recursive.equals(NO_RESPONSE)) 
            	output.append("\n").append(recursive);

        } catch (Exception e) {
            output.append("\n[Erro interno na função]");
            log.error("Erro no tool call", e);
        } 
    }
    
    private void removeUploadedFiles() {
    	uploadedFileIds.forEach(id -> {
            try { adapter.getClient().files().delete(id); }
            catch (Exception ignored) {}
        });
    	
    	uploadedFileIds.clear();
	}

    private void checkAndSummarize() {
        long inputTokens = adapter.getLastInputTokens();
        long threshold = getDynamicSummarizationThreshold();
        if (inputTokens > threshold) {
            summarizeConversation();
        }
    }

    private void summarizeConversation() {
        // Implementação similar à anterior, usando ChatMessage
        // ... (pode reutilizar lógica com mensagens temporárias)
        log.info("Sumarização automática do contexto ativada.");
    }

    private void createSystemMessage() {
        //String header = "Hoje é " + TDateUtil.formatFullgDate(new Date(), TLanguage.getLocale()) +
    	String systemPrompt = """
    			### System information:
    			Date: %s
    			User Name: %s
    			""".formatted(TDateUtil.formatFullgDate(new Date(), Locale.getDefault()), 
    					TedrosContext.getLoggedUser().getName());
    			
    			systemPrompt += (assistantPrompt != null ? assistantPrompt : "");
        messages.add(ChatCompletionMessageParam.ofSystem(ChatCompletionSystemMessageParam.builder()
        		.content(systemPrompt)
        		.build()));
    }
    
    public static void main(String[] args) {
				GrokAiTerosService service = GrokAiTerosService.create(
						"key-here", 
						"grok-4-fast-reasoning", "Voce é um assistente útil.");
				
				service.setPromptAssistant("Responda de forma clara e objetiva.");
				
		String response = service.call("Qual a capital da França?", null);
		log.info("Response: {}", response);
	} 
    
    
}
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

    private static IAiTerosService instance;
    private final GrokAiServiceAdapter adapter;

    private final List<String> uploadedFileIds = new ArrayList<>();
    private GrokAiFunctionExecutor functionExecutor;

    // Change: Added support for server-side conversation tracking
    private String lastResponseId;

    private GrokAiTerosService(String apiKey, String aiModel, String assistantPrompt) {
        this.adapter = new GrokAiServiceAdapter(apiKey, aiModel);
        setPromptAssistant(assistantPrompt);
        // createSystemMessage(); // Removed: System message is now generated on demand
        // in call()
    }

    public static IAiTerosService newInstance(String apiKey, String aiModel, String assistantPrompt) {
        return new GrokAiTerosService(apiKey, aiModel, assistantPrompt);
    }

    public static IAiTerosService create(String apiKey, String aiModel, String assistantPrompt) {
        if (instance == null)
            instance = new GrokAiTerosService(apiKey, aiModel, assistantPrompt);
        return instance;
    }

    public static IAiTerosService getInstance() {
        if (instance == null)
            throw new IllegalStateException("Instância não criada!");
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

    public String getLastResponseId() {
        return lastResponseId;
    }

    public String call(String userPrompt, String sysPrompt) {
        return call(userPrompt, sysPrompt, null);
    }

    /**
     * Call with support for explicit previous response ID.
     */
    public String call(String userPrompt, String sysPrompt, String previousResponseId) {
        List<ChatCompletionMessageParam> currentMessages = new ArrayList<>();

        // Determine if this is a new conversation or a continuation
        String effectivePreviousId = (previousResponseId != null) ? previousResponseId : this.lastResponseId;
        boolean isNewConversation = (effectivePreviousId == null);

        if (isNewConversation) {
            String fullSysPrompt = getEffectiveSystemPrompt();
            if (sysPrompt != null && !sysPrompt.isBlank()) {
                fullSysPrompt += "\n" + sysPrompt;
            }
            currentMessages.add(ChatCompletionMessageParam
                    .ofSystem(ChatCompletionSystemMessageParam.builder().content(fullSysPrompt).build()));
        }

        currentMessages.add(ChatCompletionMessageParam
                .ofUser(ChatCompletionUserMessageParam.builder().content(userPrompt).build()));

        // Call adapter with store_messages=true if new, otherwise rely on
        // previous_response_id
        ChatCompletion response = adapter.sendChatRequest(currentMessages, effectivePreviousId, isNewConversation);
        this.lastResponseId = response.id();

        String result = processResponse(response, 0);

        // checkAndSummarize(); // Removed: Handled by server-side storage
        removeUploadedFiles();

        if (!result.isEmpty() && result.contains(EMPTY_TOOL_CALL_RESPONSE)) {
            result = result.replaceAll(EMPTY_TOOL_CALL_RESPONSE, "");
            return result;
        }

        return result.isEmpty() ? NO_RESPONSE : result;
    }

    private String processResponse(ChatCompletion response, int currentDepth) {
        StringBuilder finalContent = new StringBuilder();
        var choices = response.choices();
        String currentResponseId = response.id();

        for (var choice : choices) {
            ChatCompletionMessage message = choice.message();
            Optional<String> contentOpt = message.content();
            if (contentOpt.isPresent()) {
                String content = contentOpt.get();
                if (!content.isBlank()) {
                    finalContent.append(content);
                    // Local history update removed
                }
            }

            // Processa tool calls
            message.toolCalls().ifPresent(toolCalls -> {
                for (ChatCompletionMessageToolCall toolCall : toolCalls) {
                    processToolCall(toolCall, finalContent, currentDepth, currentResponseId);
                }
            });
        }
        return finalContent.toString().trim();
    }

    private void processToolCall(ChatCompletionMessageToolCall messageToolCall, StringBuilder output, int currentDepth,
            String triggerResponseId) {

        // TRAVA DE SEGURANÇA
        if (currentDepth >= MAX_RECURSION_DEPTH) {
            log.warn("Limite de recursão de Tool Calls atingido ({})", MAX_RECURSION_DEPTH);
            return;
        }

        Optional<ChatCompletionMessageFunctionToolCall> functionToolCallOpt = messageToolCall.function();
        ChatCompletionMessageFunctionToolCall toolCall = functionToolCallOpt.get();
        log.info("Tool call detectada: {} ", toolCall);

        Optional<ToolCallResult> resultOpt = functionExecutor.callFunction(toolCall);
        if (resultOpt.isEmpty()) {
            log.info("Função não encontrada: {} (id={})", toolCall.function().name(), toolCall.id());
            output.append("\n[Função não encontrada: ").append(toolCall.function().name()).append("]");
            return;
        }

        ToolCallResult result = resultOpt.get();
        log.info("Resultado da função {} : {}", toolCall.function().name(), result);

        if (!result.isRevertToTheAIModelInCaseOfSuccess()) {
            if (output.isEmpty())
                output.append(EMPTY_TOOL_CALL_RESPONSE);
            return;
        }

        try {
            List<ChatCompletionMessageParam> toolMessages = new ArrayList<>();

            if (result.getResult() != null) {
                String resultJson = mapper.writeValueAsString(result.getResult());
                toolMessages.add(ChatCompletionMessageParam.ofTool(ChatCompletionToolMessageParam.builder()
                        .toolCallId(toolCall.id())
                        .contentAsJson(resultJson)
                        .build()));
                log.info("Resultado da função {} preparado para envio: {}", toolCall.function().name(), resultJson);
            }

            // Upload de arquivos retornados
            if ((result.getFilesContentInfo() != null && !result.getFilesContentInfo().isEmpty())) {

                // Lista para montar a mensagem multimodal do USUÁRIO
                List<ChatCompletionContentPart> contentParts = new ArrayList<>();

                // Texto introdutório
                contentParts.add(ChatCompletionContentPart.ofText(
                        ChatCompletionContentPartText.builder()
                                .text("Arquivos processados pelo sistema. Segue análise de conteúdo:")
                                .build()));

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
                            ChatCompletionContentPartText.builder().text(header).build()));

                    // 4. Se tiver texto extraído, adiciona
                    if (processed.textContent() != null && !processed.textContent().isBlank()) {
                        contentParts.add(ChatCompletionContentPart.ofText(
                                ChatCompletionContentPartText.builder()
                                        .text("Conteúdo Textual:\n" + processed.textContent())
                                        .build()));
                    }

                    // 5. Se tiver imagens (PDF renderizado ou JPG/PNG nativo), adiciona
                    for (String dataUrl : processed.base64Images()) {
                        contentParts.add(ChatCompletionContentPart.ofImageUrl(
                                ChatCompletionContentPartImage.builder()
                                        .imageUrl(ChatCompletionContentPartImage.ImageUrl.builder()
                                                .url(dataUrl) // Agora o DocumentConverter já devolve o "data:image..."
                                                              // completo
                                                .detail(ChatCompletionContentPartImage.ImageUrl.Detail.LOW)
                                                .build())
                                        .build()));
                    }
                }

                // Envia tudo como uma mensagem de User (pois contém imagens)
                toolMessages.add(ChatCompletionMessageParam.ofUser(
                        ChatCompletionUserMessageParam.builder()
                                .contentOfArrayOfContentParts(contentParts)
                                .build()));

                log.info("Contexto multimodal injetado.");
            }

            // Nova chamada recursiva
            log.info("Enviando resultado da tool call para o modelo de IA: {} (id={})",
                    toolCall.function().name(), toolCall.id());

            // Send the tool outputs, linking to the previous response ID that triggered the
            // tool
            ChatCompletion recursiveResponse = adapter.sendChatRequest(toolMessages, triggerResponseId, false);
            this.lastResponseId = recursiveResponse.id(); // Update ID tracking

            String recursive = processResponse(recursiveResponse, currentDepth + 1);

            if (!recursive.equals(NO_RESPONSE))
                output.append("\n").append(recursive);

        } catch (Exception e) {
            output.append("\n[Erro interno na função]");
            log.error("Erro no tool call", e);
        }
    }

    private void removeUploadedFiles() {
        uploadedFileIds.forEach(id -> {
            try {
                adapter.getClient().files().delete(id);
            } catch (Exception ignored) {
            }
        });

        uploadedFileIds.clear();
    }

    private String getEffectiveSystemPrompt() {
        String systemPrompt = """
                ### System information:
                Date: %s
                User Name: %s
                """.formatted(TDateUtil.formatFullgDate(new Date(), Locale.getDefault()),
                TedrosContext.getLoggedUser().getName());

        systemPrompt += (assistantPrompt != null ? assistantPrompt : "");
        return systemPrompt;
    }

    @Override
    public void cleanMessageHistory() {
        this.lastResponseId = null;
        this.uploadedFileIds.clear();
    }

}
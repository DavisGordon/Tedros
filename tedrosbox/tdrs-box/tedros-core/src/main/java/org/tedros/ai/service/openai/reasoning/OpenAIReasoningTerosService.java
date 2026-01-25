package org.tedros.ai.service.openai.reasoning;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.tedros.ai.function.TFunction;
import org.tedros.ai.function.ToolCallResult;
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

    private final OpenAiReasoningServiceAdapter adapter;
    // Local list only for initial system prompts/context, not full history
    private final List<ResponseInputItem> initialMessages = new ArrayList<>();

    private String lastUserMessage;
    private OpenAIFunctionExecutor functionExecutor;

    // Server-side conversation tracking
    private String conversationId;

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

        initialMessages.add(ResponseInputItem.ofEasyInputMessage(EasyInputMessage.builder()
                .role(EasyInputMessage.Role.SYSTEM)
                .content(userNamePrompt).build()));

        log.info("OpenAI Teros Service iniciado com sucesso. Modelo padrão: {}",
                aiModel != null ? aiModel : "não definido");
    }

    public static IAiTerosService create(String token, String aiModel, String assistantPrompt) {
        if (instance == null)
            instance = newInstance(token, aiModel, assistantPrompt);
        return instance;
    }

    public static IAiTerosService newInstance(String token, String aiModel, String assistantPrompt) {
        return new OpenAIReasoningTerosService(token, aiModel, assistantPrompt);
    }

    public static IAiTerosService getInstance() {
        if (instance == null)
            throw new IllegalStateException("Instância não criada!");
        return instance;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    @Override
    public void createFunctionExecutor(TFunction<?>... functions) {
        this.adapter.functions(Arrays.asList(functions));
        this.functionExecutor = new OpenAIFunctionExecutor(functions);
        log.info("Registradas {} função(ões) personalizada(s) para tool calls.", functions.length);
    }

    @Override
    public String call(String userPrompt, String sysPrompt) {

        log.info(">>> Iniciando nova interação com Teros (ConversationID: {})",
                conversationId != null ? conversationId : "NEW");
        log.info("Prompt do usuário: {}", userPrompt);

        lastUserMessage = userPrompt;

        List<ResponseInputItem> currentRequestMessages = new ArrayList<>();

        // Se for uma nova conversa, adiciona os prompts iniciais
        if (this.conversationId == null) {
            currentRequestMessages.addAll(this.initialMessages);
        }

        if (sysPrompt != null && !sysPrompt.isBlank()) {
            log.info("Prompt de sistema adicional: {}", sysPrompt);
            currentRequestMessages.add(adapter.buildSysMessage(sysPrompt));
        }

        currentRequestMessages.add(adapter.buildUserMessage(userPrompt));

        long startTime = System.currentTimeMillis();
        com.openai.models.responses.Response response = adapter.sendChatRequest(currentRequestMessages,
                this.conversationId);

        // Update conversation ID
        this.conversationId = response.id();

        long elapsed = System.currentTimeMillis() - startTime;

        List<ResponseOutputItem> outputItems = response.output();

        log.info(
                "Resposta da OpenAI recebida em {}ms | {} itens | ID: {} | Tokens de entrada: {} | Uso total estimado: {}",
                elapsed,
                outputItems.size(),
                this.conversationId,
                adapter.totalInputTokenProperty().get(),
                adapter.getLastUsage() != null ? adapter.getLastUsage().totalTokens() : "?");

        String output = processAiResponseMessage(outputItems, 0);

        // Check for empty tool call final response tag
        if (!output.isEmpty() && output.contains(EMPTY_TOOL_CALL_RESPONSE)) {
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

        // No need to track reasoning item for subsequent calls in server-side storage
        // mode
        // ResponseReasoningItem lastResponseReasoningItem = null;

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
                processReasoningResponse(item);
            }

            else if (item.isFunctionCall()) {
                // Process function call message
                log.info("Detectado tool call: {} (id={})", item.asFunctionCall().name(),
                        item.asFunctionCall().callId());
                processFunctionCallResponse(finalContent, item, currentDepth);
            }
        }

        String result = finalContent.toString().trim();
        return result.isEmpty() ? NO_RESPONSE : result;
    }

    // Summarize method removed as per server-side storage refactoring

    private void processFunctionCallResponse(
            StringBuilder finalContent,
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

        if (!result.isRevertToTheAIModelInCaseOfSuccess()) {
            if (finalContent.isEmpty())
                finalContent.append(EMPTY_TOOL_CALL_RESPONSE);
            return;
        }

        List<String> uploadedFileIds = new ArrayList<>(); // Para deletar depois

        try {
            // 1. Prepare Function Output
            ResponseInputItem functionCallOutput = ResponseInputItem.ofFunctionCallOutput(
                    ResponseInputItem.FunctionCallOutput.builder()
                            .callId(toolCall.callId())
                            .output(mapper.writeValueAsString(result.getResult()))
                            .build());

            // Payload temporário para enviar ao modelo
            List<ResponseInputItem> toolRequest = new ArrayList<>();
            // With server-side storage, we only send the output (continuation)
            // No need to send reasoning or function input echo
            toolRequest.add(functionCallOutput);

            // 2. Processa arquivos retornados pela função (upload + file_id)
            if (result.getFilesContentInfo() != null && !result.getFilesContentInfo().isEmpty()) {
                log.info("Tool call retornou {} arquivo(s). Fazendo upload temporário...",
                        result.getFilesContentInfo().size());

                for (TFileContentInfo fileContentInfo : result.getFilesContentInfo()) {
                    uploadFile(uploadedFileIds, toolRequest, fileContentInfo);
                }
            }

            // 3. Envia tudo de volta ao modelo
            com.openai.models.responses.Response nextResponseObj = adapter.sendToolCallResult(toolRequest,
                    this.conversationId);

            // Update conversation ID
            this.conversationId = nextResponseObj.id();

            List<ResponseOutputItem> nextResponse = nextResponseObj.output();

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
            try (ByteArrayInputStream bais = new ByteArrayInputStream(fileContentInfo.bytes())) {
                FileCreateParams uploadParams = FileCreateParams.builder()
                        // .file(bais)
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
                                .build());

                toolRequest.add(fileRefItem);
            }

            log.debug("Upload temporário concluído → {} ({} bytes)", fileContentInfo.fileName(),
                    fileContentInfo.bytes().length);

        } catch (Exception e) {
            log.error("Falha no upload do arquivo retornado pela função: {}", fileContentInfo.fileName(), e);
        }
    }

    private ResponseReasoningItem processReasoningResponse(ResponseOutputItem item) {
        ResponseReasoningItem lastResponseReasoningItem;
        Platform.runLater(() -> {
            ResponseReasoningItem reasoning = item.asReasoning();

            List<String> summaryList = reasoning.summary().stream()
                    .map(Summary::text)
                    .toList();

            if (!summaryList.isEmpty()) {
                reasoningsMessageProperty.addAll(summaryList);
            } else {
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
                    if (opt.isPresent()) {
                        String text = opt.get().text();
                        finalContent.append(text).append("\n");
                        // Note: Adapter buildAssistantMessage is no longer added to a local list here
                        log.trace("Texto do assistente processado para saída ({} chars)", text.length());
                    }
                } else if (content.isRefusal() && content.refusal().isPresent()) {
                    Optional<ResponseOutputRefusal> opt = content.refusal();
                    if (opt.isPresent()) {
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
        this.conversationId = null;
        this.adapter.resetBuilder();
    }
}
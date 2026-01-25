package org.tedros.ai.service.langchain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.slf4j.Logger;
import org.tedros.ai.function.TFunction;
import org.tedros.ai.function.ToolCallResult;
import org.tedros.ai.service.AiServiceBase;
import org.tedros.ai.service.DocumentConverter;
import org.tedros.ai.service.IAiTerosService;
import org.tedros.common.model.TFileContentInfo;
import org.tedros.core.context.TedrosContext;
import org.tedros.util.TDateUtil;
import org.tedros.util.TLoggerUtil;

import dev.langchain4j.agent.tool.ToolExecutionRequest;
import dev.langchain4j.agent.tool.ToolSpecification;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.ImageContent;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.TextContent;
import dev.langchain4j.data.message.ToolExecutionResultMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.response.ChatResponse;

public class LangChainGeminiTerosService extends AiServiceBase implements IAiTerosService {

    private static final Logger LOGGER = TLoggerUtil.getLogger(LangChainGeminiTerosService.class);

    // Limits
    private static final int MAX_RECURSION_DEPTH = 15;
    private static final int MEMORY_WINDOW_SIZE = 20;

    private static IAiTerosService instance;

    private final LangChainGeminiServiceAdapter adapter;
    private LangChainFunctionExecutor functionExecutor;
    private final MessageWindowChatMemory chatMemory;

    private LangChainGeminiTerosService(String apiKey, String aiModel, String assistantPrompt) {
        this.adapter = new LangChainGeminiServiceAdapter(apiKey, aiModel);
        this.setPromptAssistant(assistantPrompt);
        this.chatMemory = MessageWindowChatMemory.withMaxMessages(MEMORY_WINDOW_SIZE);
    }

    public static IAiTerosService create(String apiKey, String aiModel, String assistantPrompt) {
        if (instance == null) {
            instance = new LangChainGeminiTerosService(apiKey, aiModel, assistantPrompt);
        }
        return instance;
    }

    public static IAiTerosService newInstance(String apiKey, String aiModel, String assistantPrompt) {
        return new LangChainGeminiTerosService(apiKey, aiModel, assistantPrompt);
    }

    public static IAiTerosService getInstance() {
        if (instance == null)
            throw new IllegalStateException("Instance not created!");
        return instance;
    }

    @Override
    public void createFunctionExecutor(TFunction<?>... functions) {
        this.functionExecutor = new LangChainFunctionExecutor(Arrays.asList(functions));
        LOGGER.info("Registradas {} função(ões) para tool calls no Gemini (LangChain).", functions.length);
    }

    @Override
    public void setAiModel(String model) {
        LOGGER.warn("Alterar modelo on-the-fly não suportado totalmente na implementação atual do Adapter.");
    }

    @Override
    public String getAiModel() {
        return adapter.getAiModel();
    }

    @Override
    public void cleanMessageHistory() {
        chatMemory.clear();
        LOGGER.info("Histórico de conversa limpo (Gemini).");
    }

    @Override
    public String call(String userPrompt, String sysPrompt) {
        return call(userPrompt, sysPrompt, null);
    }

    public String call(String userPrompt, String sysPrompt, String previousResponseId) {

        // 1. Prepare System Message if new conversation or needed
        if (chatMemory.messages().isEmpty()) {
            String fullSysPrompt = getEffectiveSystemPrompt();
            if (sysPrompt != null && !sysPrompt.isBlank()) {
                fullSysPrompt += "\n" + sysPrompt;
            }
            chatMemory.add(SystemMessage.from(fullSysPrompt));
        }

        // 2. Add user message
        chatMemory.add(UserMessage.from(userPrompt));

        // 3. Generate response loop
        try {
            return processConversationLoop(0);
        } catch (Exception e) {
            LOGGER.error("Erro no loop de conversação do Gemini", e);
            return "Erro ao processar solicitação: " + e.getMessage();
        }
    }

    private String processConversationLoop(int depth) {
        if (depth >= MAX_RECURSION_DEPTH) {
            LOGGER.warn("Limite de recursão atingido no Gemini ({})", MAX_RECURSION_DEPTH);
            return "Limite de chamadas de ferramentas atingido.";
        }

        List<ToolSpecification> tools = (functionExecutor != null)
                ? new ArrayList<>(functionExecutor.getToolSpecifications())
                : null;

        List<ChatMessage> messages = chatMemory.messages();

        ChatResponse response = adapter.generate(messages, tools);
        AiMessage aiMessage = response.aiMessage();

        // DEBUG LOGGING
        LOGGER.info("Gemini Response - Text: {}", aiMessage.text());
        if (aiMessage.hasToolExecutionRequests()) {
            LOGGER.info("Gemini Analysis - Has {} Tool Requests", aiMessage.toolExecutionRequests().size());
            aiMessage.toolExecutionRequests()
                    .forEach(req -> LOGGER.info(" - Tool: {} args: {}", req.name(), req.arguments()));
        }

        // FIX: Handle empty responses safely (OpenAI/Gemini differences)
        // If there are tool executions, we MUST preserve the original AiMessage to keep
        // 'thought_signature' metadata.
        // If there are NO tool executions and text is null, we can safely replace it
        // with an empty string to avoid history errors.
        if (aiMessage.text() == null && !aiMessage.hasToolExecutionRequests()) {
            aiMessage = new AiMessage("");
        }

        chatMemory.add(aiMessage);

        if (aiMessage.hasToolExecutionRequests()) {
            boolean reloop = false;

            // Collect messages to add them in correct order: Tool Results first, then User
            // Content (Files)
            List<ChatMessage> toolResults = new ArrayList<>();
            List<ChatMessage> multimodalContent = new ArrayList<>();

            for (ToolExecutionRequest req : aiMessage.toolExecutionRequests()) {
                LOGGER.info("Gemini Tool Call: {} args: {}", req.name(), req.arguments());

                Optional<ToolCallResult> resultOpt = functionExecutor.execute(req);

                if (resultOpt.isPresent()) {
                    ToolCallResult res = resultOpt.get();

                    String resultJson = "{}";
                    try {
                        resultJson = new com.fasterxml.jackson.databind.ObjectMapper()
                                .writeValueAsString(res.getResult());
                    } catch (Exception e) {
                        LOGGER.error("Erro serializando resultado da tool", e);
                    }

                    toolResults.add(ToolExecutionResultMessage.from(req, resultJson));

                    // Handle files (will be added as UserMessages)
                    List<ChatMessage> files = handleMultimodalContent(res);
                    multimodalContent.addAll(files);

                    if (res.isRevertToTheAIModelInCaseOfSuccess()) {
                        reloop = true;
                    }
                } else {
                    toolResults.add(ToolExecutionResultMessage.from(req, "Function not found"));
                }
            }

            // Add to memory in correct sequence: All Tool Results, then All User File
            // Messages
            toolResults.forEach(chatMemory::add);
            multimodalContent.forEach(chatMemory::add);

            if (reloop) {
                return processConversationLoop(depth + 1);
            }
        }

        return aiMessage.text() != null ? aiMessage.text() : "";
    }

    private List<ChatMessage> handleMultimodalContent(ToolCallResult result) {
        List<ChatMessage> messages = new ArrayList<>();
        if (result.getFilesContentInfo() == null || result.getFilesContentInfo().isEmpty()) {
            return messages;
        }

        List<dev.langchain4j.data.message.Content> contentParts = new ArrayList<>();
        contentParts.add(TextContent.from("Arquivos processados pelo sistema. Segue análise de conteúdo:"));

        for (TFileContentInfo fileInfo : result.getFilesContentInfo()) {
            var processed = DocumentConverter.processFile(fileInfo.bytes(), fileInfo.fileName());

            String header = String.format("\n=== ARQUIVO: %s ===\n", fileInfo.fileName());
            contentParts.add(TextContent.from(header));

            if (processed.textContent() != null && !processed.textContent().isBlank()) {
                contentParts.add(TextContent.from("Conteúdo Textual:\n" + processed.textContent()));
            }

            for (String dataUrl : processed.base64Images()) {
                contentParts.add(ImageContent.from(dataUrl));
            }
        }

        messages.add(UserMessage.from(contentParts));
        return messages;
    }

    private String getEffectiveSystemPrompt() {
        String systemPrompt = """
                ### System information:
                Date: %s
                User Name: %s
                """.formatted(TDateUtil.formatFullgDate(new Date(), Locale.getDefault()),
                TedrosContext.getLoggedUser().getName());

        systemPrompt += (assistantPrompt != null ? assistantPrompt : "");

        // FIX: Enforce strict tool call format to prevent Gemini 2.5 Flash from
        // generating Python code (MALFORMED_FUNCTION_CALL)
        systemPrompt += """

                ==================================================
                TOOL CALLING RULES (ABSOLUTE)
                ==================================================
                - You MUST use the provided tools to perform actions.
                - Do NOT hallucinate Python code, 'print()' statements, or 'default_api' references.
                - Tool calls must be standard, raw JSON adhering strictly to the provided function schemas.
                - Never output code to 'simulate' a tool call. Execute it directly via the API.
                """;

        return systemPrompt;
    }
}

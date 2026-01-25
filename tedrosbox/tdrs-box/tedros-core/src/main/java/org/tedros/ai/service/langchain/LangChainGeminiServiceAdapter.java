package org.tedros.ai.service.langchain;

import java.time.Duration;
import java.util.List;

import org.slf4j.Logger;
import org.tedros.util.TLoggerUtil;

import dev.langchain4j.agent.tool.ToolSpecification;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.ToolExecutionResultMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.request.ChatRequest;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;

/**
 * Adapter genérico para o modelo Google Gemini usando LangChain4j.
 */
public class LangChainGeminiServiceAdapter {

    private static final Logger LOGGER = TLoggerUtil.getLogger(LangChainGeminiServiceAdapter.class);

    private final ChatModel model;
    private final String modelName;

    public LangChainGeminiServiceAdapter(String apiKey, String modelName) {
        this.modelName = modelName;

        this.model = GoogleAiGeminiChatModel.builder()
                .apiKey(apiKey)
                .modelName(modelName)
                .timeout(Duration.ofSeconds(120))
                .logRequestsAndResponses(true)
                .build();
    }

    public ChatResponse generate(List<ChatMessage> messages, List<ToolSpecification> tools) {
        try {
            ChatRequest request = ChatRequest.builder()
                    .messages(messages)
                    .toolSpecifications(tools)
                    .build();

            // DEBUG: Log message structure to debug "Invalid turn order" errors
            LOGGER.info("Gemini Request - Message Count: {}", messages.size());
            for (int i = 0; i < messages.size(); i++) {
                ChatMessage m = messages.get(i);

                if (m instanceof AiMessage msg) {
                    LOGGER.info(" [{}] Type: {} | Content: {}", i, m.type(),
                            (msg.text() != null
                                    ? (msg.text().length() > 50 ? msg.text().substring(0, 50) + "..." : msg.text())
                                    : "[NON-TEXT]"));
                }

                if (m instanceof UserMessage msg) {
                    StringBuilder sb = new StringBuilder();
                    for (dev.langchain4j.data.message.Content c : msg.contents()) {
                        sb.append(c.toString()).append(" ");
                    }
                    String content = sb.toString();
                    LOGGER.info(" [{}] Type: {} | Content: {}", i, m.type(),
                            (content.length() > 50 ? content.substring(0, 50) + "..." : content));
                }

                if (m instanceof SystemMessage msg) {
                    LOGGER.info(" [{}] Type: {} | Content: {}", i, m.type(),
                            (msg.text() != null
                                    ? (msg.text().length() > 50 ? msg.text().substring(0, 50) + "..." : msg.text())
                                    : "[NON-TEXT]"));
                }

                if (m instanceof ToolExecutionResultMessage msg) {
                    LOGGER.info(" [{}] Type: {} | Content: {}", i, m.type(),
                            (msg.text() != null
                                    ? (msg.text().length() > 50 ? msg.text().substring(0, 50) + "..." : msg.text())
                                    : "[NON-TEXT]"));
                }

            }

            return model.chat(request);

        } catch (Exception e) {
            LOGGER.error("Erro na geração da resposta com LangChain4j (Gemini): {}", e.getMessage(), e);
            throw new RuntimeException("Erro ao chamar serviço de IA Gemini", e);
        }
    }

    public String getAiModel() {
        return modelName;
    }
}

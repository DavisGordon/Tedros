package org.tedros.ai.service.langchain;

import java.time.Duration;
import java.util.List;

import org.slf4j.Logger;
import org.tedros.util.TLoggerUtil;

import dev.langchain4j.agent.tool.ToolSpecification;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.request.ChatRequest;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.openai.OpenAiChatModel;

/**
 * Adapter genérico para o modelo OpenAI/Grok usando LangChain4j.
 */
public class LangChainServiceAdapter {

    private static final Logger LOGGER = TLoggerUtil.getLogger(LangChainServiceAdapter.class);

    private final ChatModel model;
    private final String modelName;

    public LangChainServiceAdapter(String apiKey, String modelName, String baseUrl) {
        this.modelName = modelName;

        OpenAiChatModel.OpenAiChatModelBuilder builder = OpenAiChatModel.builder()
                .apiKey(apiKey)
                .modelName(modelName)
                .timeout(Duration.ofSeconds(120))
                .logRequests(true)
                .logResponses(true);

        if (baseUrl != null && !baseUrl.isBlank()) {
            builder.baseUrl(baseUrl);
        }

        this.model = builder.build();
    }

    public ChatResponse generate(List<ChatMessage> messages, List<ToolSpecification> tools) {
        try {
            ChatRequest request = ChatRequest.builder()
                    .messages(messages)
                    .toolSpecifications(tools)
                    .build();

            return model.chat(request);

        } catch (Exception e) {
            LOGGER.error("Erro na geração da resposta com LangChain4j: {}", e.getMessage(), e);
            throw new RuntimeException("Erro ao chamar serviço de IA", e);
        }
    }

    public String getAiModel() {
        return modelName;
    }
}

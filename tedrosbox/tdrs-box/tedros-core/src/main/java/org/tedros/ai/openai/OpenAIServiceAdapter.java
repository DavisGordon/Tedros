package org.tedros.ai.openai;

import java.util.List;

import org.slf4j.Logger;
import org.tedros.util.TLoggerUtil;

import com.openai.core.JsonValue;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import com.openai.models.chat.completions.ChatCompletionMessage;
import com.openai.models.chat.completions.ChatCompletionMessageParam;

/**
 * Adaptador genérico para criar requisições de chat.
 */
public class OpenAIServiceAdapter {

    private static final Logger LOGGER = TLoggerUtil.getLogger(OpenAIServiceAdapter.class);

    private final com.openai.client.OpenAIClient client;

    public OpenAIServiceAdapter(String apiKey) {
        this.client = OpenAIClientFactory.getClient(apiKey);
    }

    public ChatCompletion sendChatRequest(String model, List<ChatCompletionMessageParam> messages) {
        try {
            ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
                    .model(model)
                    .messages(messages)
                    .temperature(1.0)
                    .maxTokens(2000)
                    .build();

            return client.chat().completions().create(params);
        } catch (Exception e) {
            LOGGER.error("Erro ao chamar OpenAI: {}", e.getMessage());
            throw new RuntimeException("Erro ao chamar OpenAI", e);
        }
    }

    public ChatCompletionMessage buildMessage(JsonValue role, String content) {
        return ChatCompletionMessage.builder()
                .role(role)
                .content(content)
                .build();
    }
}


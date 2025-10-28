package org.tedros.ai.openai;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;

/**
 * Fabrica para criação do cliente OpenAI (SDK oficial).
 */
public final class OpenAIClientFactory {

    private static OpenAIClient clientInstance;

    private OpenAIClientFactory() {
    }

    public static OpenAIClient getClient(String apiKey) {
        if (clientInstance == null) {
            synchronized (OpenAIClientFactory.class) {
                if (clientInstance == null) {
                    clientInstance = OpenAIOkHttpClient.builder()
                            .apiKey(apiKey)
                            .build();
                }
            }
        }
        return clientInstance;
    }
}

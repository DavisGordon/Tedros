package org.tedros.ai.openai;

import static java.util.stream.Collectors.toList;

import java.util.List;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatModel;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import com.openai.models.chat.completions.ChatCompletionDeveloperMessageParam;
import com.openai.models.chat.completions.ChatCompletionMessage;
import com.openai.models.chat.completions.ChatCompletionMessageParam;
import com.openai.models.chat.completions.ChatCompletionUserMessageParam;

public final class CompletionsConversationExample {
    private CompletionsConversationExample() {}

    public static void main(String[] args) {
        // Configures using one of:
        // - The `OPENAI_API_KEY` environment variable
        // - The `OPENAI_BASE_URL` and `AZURE_OPENAI_KEY` environment variables
        OpenAIClient client = OpenAIClientFactory.getClient("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");

        
        List<ChatCompletionMessageParam> systemMessages = List.of(
        		ChatCompletionMessageParam.ofDeveloper(ChatCompletionDeveloperMessageParam.builder()
        				.content("Make sure you mention Stainless!").build()),
        		ChatCompletionMessageParam.ofUser(ChatCompletionUserMessageParam.builder()
        				.content("Tell me a story about building the best SDK!").build())
		);
        
        
        // Use a builder so that we can append more messages to it below.
        // Each time we call .build()` we get an immutable object that's unaffected by future mutations of the builder.
        ChatCompletionCreateParams.Builder createParamsBuilder = ChatCompletionCreateParams.builder()
                .model(ChatModel.GPT_4_TURBO)
                .maxCompletionTokens(2048)
				.messages(systemMessages);

        for (int i = 0; i < 4; i++) {
            List<ChatCompletionMessage> messages =
                    client.chat().completions().create(createParamsBuilder.build()).choices().stream()
                            .map(ChatCompletion.Choice::message)
                            .collect(toList());

            messages.stream().flatMap(message -> message.content().stream()).forEach(System.out::println);

            System.out.println("\n-----------------------------------\n");

            messages.forEach(createParamsBuilder::addMessage);
            createParamsBuilder
                    .addDeveloperMessage("Be as snarky as possible when replying!" + "!".repeat(i))
                    .addUserMessage("But why?" + "?".repeat(i));
        }
    }
}

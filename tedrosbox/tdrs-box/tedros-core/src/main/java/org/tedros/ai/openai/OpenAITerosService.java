package org.tedros.ai.openai;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.tedros.core.TLanguage;
import org.tedros.core.context.TedrosContext;
import org.tedros.util.TDateUtil;
import org.tedros.util.TLoggerUtil;

import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionMessage;

/**
 * Versão adaptada do TerosService usando o SDK oficial.
 */
public class OpenAITerosService {

    private static final Logger LOGGER = TLoggerUtil.getLogger(OpenAITerosService.class);

    private final OpenAIServiceAdapter adapter;
    private final List<ChatCompletionMessage> messages = new ArrayList<>();
    private OpenAIFunctionExecutor functionExecutor;
    private static String GPT_MODEL;
    private static String PROMPT_ASSISTANT;
    private static OpenAITerosService instance;

    private OpenAITerosService(String token) {
        this.adapter = new OpenAIServiceAdapter(token);
        createSystemMessage();
        LOGGER.info("OpenAI Teros Service iniciado!");
    }

    public static OpenAITerosService create(String token) {
        if (instance == null)
            instance = new OpenAITerosService(token);
        return instance;
    }

    public static OpenAITerosService getInstance() {
        if (instance == null)
            throw new IllegalStateException("Instância não criada!");
        return instance;
    }

    private void createSystemMessage() {
        String date = TDateUtil.formatFullgDate(new Date(), TLanguage.getLocale());
        String user = TedrosContext.getLoggedUser().getName();
        String header = "Today is %s. You are Teros, a smart and helpful assistant for the Tedros desktop system. Engage intelligently with user %s."
                .formatted(date, user);
        if (PROMPT_ASSISTANT != null)
            header += " " + PROMPT_ASSISTANT;

        messages.add(adapter.buildMessage(Role.SYSTEM, header));
    }

    public void createFunctionExecutor(org.tedros.ai.function.TFunction<?>... functions) {
        this.functionExecutor = new OpenAIFunctionExecutor(functions);
    }

    public String call(String userPrompt, String sysPrompt) {
        if (sysPrompt != null)
            messages.add(adapter.buildMessage(Role.SYSTEM, sysPrompt));

        messages.add(adapter.buildMessage(Role.USER, userPrompt));

        ChatCompletion completion = adapter.sendChatRequest(GPT_MODEL, messages);

        // Obtem a primeira mensagem do modelo
        var response = completion.getChoices().get(0).getMessage();
        messages.add(response);

        String content = response.getContent();

        // TODO: implementar detecção de tool calls, caso haja suporte no SDK.
        return content != null ? content : "[no response]";
    }

    public static void setGptModel(String model) {
        GPT_MODEL = model;
    }

    public static void setPromptAssistant(String prompt) {
        PROMPT_ASSISTANT = prompt;
    }
}

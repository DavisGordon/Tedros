package org.tedros.ai;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.tedros.ai.function.TFunction;
import org.tedros.core.TLanguage;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.security.model.TUser;
import org.tedros.util.TDateUtil;
import org.tedros.util.TLoggerUtil;

import com.theokanning.openai.OpenAiHttpException;
import com.theokanning.openai.Usage;
import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionRequest.ChatCompletionRequestFunctionCall;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatFunction;
import com.theokanning.openai.completion.chat.ChatFunctionCall;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.FunctionExecutor;
import com.theokanning.openai.service.OpenAiService;

public class TerosService {

	private static String GPT_MODEL = null;
	private final static Logger LOGGER = TLoggerUtil.getLogger(TerosService.class);
	private static TerosService instance;
	
	private static String PROMPT_ASSISTANT = null;
	private static String PROMPT_HEADER = "Today is %s. You are Teros, a smart and helpful assistant for the Tedros desktop system. "+
									"Engage intelligently and efficiently with the user %s. ";
	

	private List<ChatMessage> messages = new ArrayList<>();
	private FunctionExecutor functionExecutor;
	private OpenAiService service;

	/**
	 * @param token
	 */
	private TerosService(String token) {
		super();
		
		service = new OpenAiService(token, Duration.ZERO);
		createSystemMessage();
		LOGGER.info("Teros Ai Service created!");
	}

	private void createSystemMessage() {
		String date = TDateUtil.formatFullgDate(new Date(), TLanguage.getLocale());
		StringBuilder msg = new StringBuilder(PROMPT_HEADER.formatted(date, TedrosContext.getLoggedUser().getName()));
		if(PROMPT_ASSISTANT!=null)
			msg.append(PROMPT_ASSISTANT);
		ChatMessage systemMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(), msg.toString());
		messages.add(systemMessage);
	}

	public static TerosService create(String token) {
		if (instance == null)
			instance = new TerosService(token);
		return instance;
	}

	public static TerosService getInstance() {
		if (instance == null)
			throw new IllegalStateException("The instance was not created!");
		return instance;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void createFunctionExecutor(TFunction... functions) {

		functionExecutor = null;
		if (functions != null && functions.length > 0) {
			List<ChatFunction> lst = new ArrayList<>();
			for (TFunction f : functions) {
				lst.add(ChatFunction.builder()
						.name(TLanguage.getInstance().getString(f.getName()))
						.description(TLanguage.getInstance().getString(f.getDescription()))
						.executor(f.getModel(), f.getCallback()).build());
			}
			functionExecutor = new FunctionExecutor(lst);
			LOGGER.info("FunctionExecutor created with functions: "+lst);
		}
	}

	public String call(String userPrompt, String sysPrompt) {

		String resp = "";
		if (sysPrompt != null)
			messages.add(new ChatMessage(ChatMessageRole.SYSTEM.value(), sysPrompt));
		ChatMessage msg = new ChatMessage(ChatMessageRole.USER.value(), userPrompt);
		messages.add(msg);
		while (true) {
			ChatCompletionRequest chatCompletionRequest = buildRequest();
			try {
				ChatCompletionResult result = service.createChatCompletion(chatCompletionRequest);
				
				ChatCompletionChoice choice = result.getChoices().get(0);
				ChatMessage responseMessage = choice.getMessage();
				messages.add(responseMessage); 
				
				Usage usage = result.getUsage();
				LOGGER.info(String.format("Total messages: %s, Usage Tokens: prompt=%s, completion=%s, total=%s", messages.size(), usage.getPromptTokens(), usage.getCompletionTokens(), usage.getTotalTokens()));
				
				resp = responseMessage.getContent();

				ChatFunctionCall functionCall = responseMessage.getFunctionCall();
				if (functionExecutor != null && functionCall != null) {
					LOGGER.info("Trying to execute " + functionCall.getName() + "...");
					try {
						ChatMessage message = functionExecutor.executeAndConvertToMessage(functionCall);
						
						if (message != null) {
							LOGGER.debug("Executed " + functionCall.getName() + ".");
							messages.add(message);
							continue;
						} else {
							LOGGER.info(
								"Something went wrong with the execution of " + functionCall.getName() + "...");
							break;
						}
					} catch (Exception e) {
						LOGGER.error(e.getMessage(), e);
						ChatMessage message = functionExecutor.convertExceptionToMessage(e);
						messages.add(message);
						resp = "Error: " + e.getMessage();
					}
				}
			} catch (Exception e) {
				
				if(e instanceof OpenAiHttpException 
						&& ((OpenAiHttpException)e).code.equals("context_length_exceeded")) {

					LOGGER.warn("Total Messages="+messages.size(), e);
					
					do {
						messages.remove(1);
					}while(messages.size()>10);
					
					continue;
				}

				LOGGER.error(e.getMessage(), e);
				resp = "Error: " + e.getMessage();
			}

			break;
		}

		return resp;
	}

	public ChatCompletionRequest buildRequest() {
		TUser u = TedrosContext.getLoggedUser();
		return (this.functionExecutor != null)
				? ChatCompletionRequest.builder().model(GPT_MODEL).messages(messages)
						.user(String.valueOf(u.getLogin().hashCode()))
						.functions(functionExecutor.getFunctions())
						.functionCall(ChatCompletionRequestFunctionCall.of("auto"))
						.n(1).temperature(1.0)
						.maxTokens(2000)
						.logitBias(new HashMap<>())
						.build()
				: ChatCompletionRequest.builder().model(GPT_MODEL).messages(messages)
						.user(String.valueOf(u.getLogin().hashCode()))
						.n(1).temperature(1.0)
						.maxTokens(2000)
						.logitBias(new HashMap<>())
						.build();
	}

	public void clearMessages() {
		messages.clear();
		createSystemMessage();
	}

	public static void setGptModel(String model) {
		GPT_MODEL = model;
	}

	public static void setPromptAssistant(String prompt) {
		PROMPT_ASSISTANT = prompt;
	}

}
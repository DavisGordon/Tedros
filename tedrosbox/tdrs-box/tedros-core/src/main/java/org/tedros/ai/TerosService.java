package org.tedros.ai;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.tedros.ai.function.TFunction;
import org.tedros.core.TLanguage;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.security.model.TUser;

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

	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static TerosService instance;

	private List<ChatMessage> messages = new ArrayList<>();
	private FunctionExecutor functionExecutor;
	private OpenAiService service;

	/**
	 * @param token
	 */
	private TerosService(String token) {
		super();
		service = new OpenAiService(token, Duration.ZERO);
		String msg = "You are a helpful assistant for the Tedros desktop system and your name is Teros."
				+ "\nBe smart with the user " + TedrosContext.getLoggedUser().getName() 
				+ "\nImportant: Never generate random or fictitious data unless requested by the user, always try to use system data provided by the functions"
				+ ", do this before helping the user."
				/*+ "\nImportant: before  use the functions to help the user and call the list_system_views function to get system information, "
				+ "do this before helping the user"*/;
		ChatMessage systemMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(), msg);
		messages.add(systemMessage);
		LOGGER.info("Teros Ai Service created!");
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
							LOGGER.info("Executed " + functionCall.getName() + ".");
							messages.add(message);
							continue;
						} else {
							LOGGER.info(
								"Something went wrong with the execution of " + functionCall.getName() + "...");
							break;
						}
					} catch (Exception e) {
						//e.printStackTrace();
						LOGGER.log(Level.ALL, e.getMessage(), e);
						ChatMessage message = functionExecutor.convertExceptionToMessage(e);
						messages.add(message);
						resp = "Error: " + e.getMessage();
					}
				}
			} catch (Exception e) {
				
				if(e instanceof OpenAiHttpException 
						&& ((OpenAiHttpException)e).code.equals("context_length_exceeded")) {

					LOGGER.log(Level.ALL, "Total Messages="+messages.size(), e);
					
					do {
						messages.remove(1);
					}while(messages.size()>10);
					
					continue;
				}

				LOGGER.log(Level.ALL, e.getMessage(), e);
				resp = "Error: " + e.getMessage();
			}

			break;
		}

		return resp;
	}

	public ChatCompletionRequest buildRequest() {
		TUser u = TedrosContext.getLoggedUser();
		return (this.functionExecutor != null)
				? ChatCompletionRequest.builder().model("gpt-3.5-turbo-16k").messages(messages)
						.user(String.valueOf(u.getLogin().hashCode()))
						.functions(functionExecutor.getFunctions())
						.functionCall(ChatCompletionRequestFunctionCall.of("auto"))
						.n(1).temperature(1.0)
						.maxTokens(2000).logitBias(new HashMap<>()).build()
				: ChatCompletionRequest.builder().model("gpt-3.5-turbo-16k").messages(messages)
						.user(String.valueOf(u.getLogin().hashCode()))
						.n(1).temperature(1.0)
						.maxTokens(2000).logitBias(new HashMap<>()).build();
	}

}
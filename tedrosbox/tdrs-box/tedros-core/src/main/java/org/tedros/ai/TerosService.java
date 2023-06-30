package org.tedros.ai;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.tedros.ai.function.TFunction;
import org.tedros.core.context.TedrosContext;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionRequest.ChatCompletionRequestFunctionCall;
import com.theokanning.openai.completion.chat.ChatFunction;
import com.theokanning.openai.completion.chat.ChatFunctionCall;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.FunctionExecutor;
import com.theokanning.openai.service.OpenAiService;

public class TerosService{

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
				+ "\nIntroduce yourself by greeting the user "+TedrosContext.getLoggedUser().getName()
				+"\nImportant: call the list_system_views function to get system information, do this before helping the user";
        ChatMessage systemMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(), msg);
        messages.add(systemMessage);
	}
	
	public static TerosService create(String token) {
		if(instance==null)
			instance = new TerosService(token);
		return instance;
	}
	
	public static TerosService getInstance() {
		if(instance==null)
			throw new IllegalStateException("The instance was not created!");
		return instance;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void createFunctionExecutor(TFunction... functions) {
		functionExecutor = null;
		if(functions!=null && functions.length>0) {
			List<ChatFunction> lst = new ArrayList<>();
			for(TFunction f : functions) {
				lst.add(ChatFunction.builder()
                .name(f.getName())
                .description(f.getDescription())
                .executor(f.getModel(), f.getCallback())
                .build());
			}
			functionExecutor = new FunctionExecutor(lst);
		}
	}

	public String call(String userPrompt, String sysPrompt) {

        String resp = "";
        if(sysPrompt!=null)
        	messages.add(new ChatMessage(ChatMessageRole.SYSTEM.value(), sysPrompt));
        ChatMessage msg = new ChatMessage(ChatMessageRole.USER.value(), userPrompt);
        messages.add(msg);
        while (true) {
	        ChatCompletionRequest chatCompletionRequest = buildRequest();
	        
	        ChatMessage responseMessage = service
	        		.createChatCompletion(chatCompletionRequest)
	        		.getChoices().get(0).getMessage();
	        
	        messages.add(responseMessage); // don't forget to update the conversation with the latest response

            resp = responseMessage.getContent();
           
            ChatFunctionCall functionCall = responseMessage.getFunctionCall();
            if (functionExecutor!=null && functionCall != null) {
            	System.out.println("Trying to execute " + functionCall.getName() + "...");
            	try {
            	   ChatMessage message = functionExecutor
                		.executeAndConvertToMessage(functionCall);
                /* You can also try 'executeAndConvertToMessage' inside a try-catch block, and add the following line inside the catch:
                "message = executor.handleException(exception);"
                The content of the message will be the exception itself, so the flow of the conversation will not be interrupted, and you will still be able to log the issue. */

                if (message!=null) {
                    /* At this point:
                    1. The function requested was found
                    2. The request was converted to its specified object for execution (Weather.class in this case)
                    3. It was executed
                    4. The response was finally converted to a ChatMessage object. */

                    System.out.println("Executed " + functionCall.getName() + ".");
                    messages.add(message);
                    continue;
                } else {
                    System.out.println("Something went wrong with the execution of " + functionCall.getName() + "...");
                    break;
                }
            	}catch(Exception e) {
            		e.printStackTrace();
            		 ChatMessage message = functionExecutor.convertExceptionToMessage(e);
            		 messages.add(message);
            		 resp = "Error: "+e.getMessage();
            	}
            }

            break;
        }
        
        return resp;
    }

	public ChatCompletionRequest buildRequest() {
		return (this.functionExecutor!=null)
				? ChatCompletionRequest
		        .builder()
		        .model("gpt-3.5-turbo-16k")
		        .messages(messages)
		        .functions(functionExecutor.getFunctions())
		        .functionCall(ChatCompletionRequestFunctionCall.of("auto"))
		        .n(1)
		        .temperature(0.1)
		        .maxTokens(2000)
		        .logitBias(new HashMap<>())
		        .build()
		        : ChatCompletionRequest
		        .builder()
		        .model("gpt-3.5-turbo-16k")
		        .messages(messages)
		        .n(1)
		        .temperature(0.1)
		        .maxTokens(2000)
		        .logitBias(new HashMap<>())
		        .build();
	}

}
package org.tedros.ai;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.theokanning.openai.completion.chat.*;
import com.theokanning.openai.completion.chat.ChatCompletionRequest.ChatCompletionRequestFunctionCall;
import com.theokanning.openai.service.FunctionExecutor;
import com.theokanning.openai.service.OpenAiService;

import java.util.*;

import org.tedros.ai.function.TFunction;
import org.tedros.ai.function.model.CallView;
import org.tedros.ai.function.model.Empty;
import org.tedros.ai.function.model.Response;
import org.tedros.ai.function.model.ViewCatalog;

public class TerosService{

	public  static String token = "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";
	
	/*public static void main(String[] args) {
		TerosService s = new TerosService(token);
		ViewCatalog log = new ViewCatalog();
		log.add("Produtos", "Gerencia todos os produtos", "App/Estoque/Produtos");
		log.add("Entrada no estoque", "Registra entrada de produto no estoque", "App/Estoque/Entrada");
		log.add("Saida do estoque", "Registra saida de produto do estoque", "App/Estoque/Saida");
		log.add("Relatorio de inventario", "Gera relatorio do inventario", "App/Estoque/Relatorio");
		log.add("Clientes", "Gerencia todos os clientes", "App/Suporte/Pessoas!Clientes");
		log.add("Funcionarios", "Gerencia todos os funcionarios", "App/Suporte/Pessoas!Funcionario");
		log.add("Empresas", "Gerencia todas as Empresas", "App/Suporte/Pessoas/Empresas ");
		
		TFunction<Empty> f1 = new TFunction<Empty>("list_system_views", "List all system views/screens", Empty.class, obj->log);
		TFunction<CallView> f2 = new TFunction<CallView>("call_view", "Call and open a view/screen", CallView.class, 
				obj->new Response(obj.getSystemViewPath() + " opened successfully"));
		
		s.createFunctionExecutor(f1, f2);
		
		String resp = "";
        System.out.print("First Query: ");
        Scanner scanner = new Scanner(System.in);
        resp = s.call(scanner.nextLine());
        while(true) {
            System.out.println("Response: " + resp);
            System.out.print("Next Query: ");
            String nextLine = scanner.nextLine();
            if (nextLine.equalsIgnoreCase("exit")) {
            	scanner.close();
                System.exit(0);
            }
            resp = s.call(nextLine);
        }
		
	}*/

	private List<ChatMessage> messages = new ArrayList<>();
	private FunctionExecutor functionExecutor;
    private OpenAiService service;
    /**
	 * @param token
	 */
	public TerosService(String token) {
		super();
		service = new OpenAiService(token);

        ChatMessage systemMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(), 
        		"Você é um útil assistente do sistema Tedros e seu nome é Teros");
        messages.add(systemMessage);
	}
	
	@SuppressWarnings("unchecked")
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

	public String call(String prompt) {

        String resp = "";
        ChatMessage msg = new ChatMessage(ChatMessageRole.USER.value(), prompt);
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
            		 ChatMessage message = functionExecutor.convertExceptionToMessage(e);
            		 messages.add(message);
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
		        .model("gpt-3.5-turbo-0613")
		        .messages(messages)
		        .functions(functionExecutor.getFunctions())
		        .functionCall(ChatCompletionRequestFunctionCall.of("auto"))
		        .n(1)
		        .maxTokens(2500)
		        .logitBias(new HashMap<>())
		        .build()
		        : ChatCompletionRequest
		        .builder()
		        .model("gpt-3.5-turbo-0613")
		        .messages(messages)
		        .n(1)
		        .maxTokens(2500)
		        .logitBias(new HashMap<>())
		        .build();
	}

}
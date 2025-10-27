package org.tedros.ai.openai;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.tedros.ai.function.TFunction;
import org.tedros.ai.openai.model.ToolCallResult;
import org.tedros.core.TLanguage;
import org.tedros.util.TDateUtil;
import org.tedros.util.TLoggerUtil;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.openai.models.ChatModel;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletion.Choice;
import com.openai.models.chat.completions.ChatCompletionMessageFunctionToolCall;
import com.openai.models.chat.completions.ChatCompletionMessageParam;
import com.openai.models.chat.completions.ChatCompletionMessageToolCall;
import com.openai.models.chat.completions.ChatCompletionToolMessageParam;

/**
 * Versão adaptada do TerosService usando o SDK oficial.
 */
public class OpenAITerosService {

    private static final Logger LOGGER = TLoggerUtil.getLogger(OpenAITerosService.class);

    private final OpenAIServiceAdapter adapter;
    private final List<ChatCompletionMessageParam> messages = new ArrayList<>();
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
        //String user = TedrosContext.getLoggedUser().getName();
        String user = "Davis";
        String header = "Today is %s. You are Teros, a smart and helpful assistant for the Tedros desktop system. Engage intelligently with user %s."
                .formatted(date, user);
        
        if (PROMPT_ASSISTANT != null)
            header += " " + PROMPT_ASSISTANT;

        messages.add(adapter.buildSysMessage(header));
    }

    public void createFunctionExecutor(org.tedros.ai.function.TFunction<?>... functions) {
    	this.adapter.functions(Arrays.asList(functions));
    	this.functionExecutor = new OpenAIFunctionExecutor(functions);
    }

    public String call(String userPrompt, String sysPrompt) {
        if (sysPrompt != null)
            messages.add(adapter.buildSysMessage(sysPrompt));

        messages.add(adapter.buildUserMessage(userPrompt));

        ChatCompletion completion = adapter.sendChatRequest(GPT_MODEL, messages);

        return processChatCompletion(completion);
    }

	private String processChatCompletion(ChatCompletion completion) {
		// Obtem a primeira mensagem do modelo
        String content = null;
        try {
            // SDK provides choices() or getChoices(); try both patterns defensively
            List<Choice> choices = completion.choices();

            if (choices != null && !choices.isEmpty()) {
                Choice first = choices.get(0);
                if(first.isValid()) {
                	Optional<String> messageOpt = first.message().content();
                	if(messageOpt.isPresent()) {
						content = messageOpt.get();
					}
                	
                	Optional<List<ChatCompletionMessageToolCall>> optMsgToolCall = first.message().toolCalls();
                	if(optMsgToolCall.isPresent() && functionExecutor!=null) {
						List<ChatCompletionMessageToolCall> toolCalls = optMsgToolCall.get();
						
						for(ChatCompletionMessageToolCall toolCall : toolCalls) {
							Optional<ChatCompletionMessageFunctionToolCall> optFunction = toolCall.function();
							if(optFunction.isEmpty())
								continue;
							
							ChatCompletionMessageFunctionToolCall function = optFunction.get();
														
							Optional<ToolCallResult> resultOpt = functionExecutor.callFunction(function.function());
							
							if(resultOpt.isPresent()) {
								ChatCompletion completion2 = adapter.sendToolCallResult(GPT_MODEL, messages, toolCall.asFunction().id(), resultOpt.get());
								content = processChatCompletion(completion2);
							}else {
								LOGGER.warn("Função {} não encontrada!", function.function().name());
							}
						}
						
					}
                	
				}else {
					//TODO: tratar resposta inválida
					content = first.finishReason().toString();
				}
            }
        } catch (Exception e) {
            LOGGER.error("Erro ao processar resposta do OpenAI: {}", e.getMessage());
        }

        // adiciona resposta ao histórico se possível (constrói como message param)
        if (content != null) {
            messages.add(adapter.buildAssistantMessage(content));
        }

        // TODO: implementar detecção de tool calls, caso haja suporte no SDK.
        return content != null ? content : "[no response]";
	}

    public static void setGptModel(String model) {
        GPT_MODEL = model;
    }

    public static void setPromptAssistant(String prompt) {
        PROMPT_ASSISTANT = prompt;
    }
    
    @JsonClassDescription("The person object containing the name.")
    static class Pessoa {
    	@JsonPropertyDescription("The person name")
    	private String param1;
    	
    	@JsonPropertyDescription("The person last name")
    	private String param2;

		public String getParam1() {
			return param1;
		}

		public void setParam1(String param1) {
			this.param1 = param1;
		}

		public String getParam2() {
			return param2;
		}

		public void setParam2(String param2) {
			this.param2 = param2;
		}
    	
    	
    }
        
	public static void main(String[] args) {
    	OpenAITerosService service = OpenAITerosService.create(System.getenv("OPENAI_API_KEY"));
    	service.createFunctionExecutor(new TFunction<Pessoa>("search_person", "Search for a person", Pessoa.class, 
    			v->{
    				Pessoa p = new Pessoa();
    				p.setParam2("Rodrigues");
    				return p;
    			}));
    	
		service.setGptModel(ChatModel.GPT_4_TURBO.toString());
		String response = service.call("Qual o sobre nome do Nando?", "Never reply with another question, use the search_person function to find people.");
		System.out.println(response);
		/*
		for (int i = 0; i < 4; i++) {            
			
            System.out.println("\n-----------------------------------\n");

            response = service.call("But why?" + "?".repeat(i), "Be as snarky as possible when replying!" + "!".repeat(i));
            System.out.println(response);
        }*/
		
		
	}
}
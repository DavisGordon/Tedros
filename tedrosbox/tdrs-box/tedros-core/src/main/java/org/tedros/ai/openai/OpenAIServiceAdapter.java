package org.tedros.ai.openai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.tedros.ai.function.TFunction;
import org.tedros.ai.openai.model.ToolCallResult;
import org.tedros.util.TLoggerUtil;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import com.openai.client.OpenAIClient;
import com.openai.core.JsonValue;
import com.openai.models.FunctionDefinition;
import com.openai.models.FunctionParameters;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionAssistantMessageParam;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import com.openai.models.chat.completions.ChatCompletionFunctionTool;
import com.openai.models.chat.completions.ChatCompletionMessageParam;
import com.openai.models.chat.completions.ChatCompletionSystemMessageParam;
import com.openai.models.chat.completions.ChatCompletionTool;
import com.openai.models.chat.completions.ChatCompletionToolMessageParam;
import com.openai.models.chat.completions.ChatCompletionUserMessageParam;

/**
 * Adaptador genérico para criar requisições de chat.
 */
public class OpenAIServiceAdapter {

    private static final Logger LOGGER = TLoggerUtil.getLogger(OpenAIServiceAdapter.class);
    
    private static long maxTokens = 2000;
    private static ObjectMapper mapper = new ObjectMapper();
    private static JsonSchemaGenerator schemaGen = new JsonSchemaGenerator(mapper);

    private final OpenAIClient client;
    
    private ChatCompletionCreateParams.Builder builder;
    
    private List<ChatCompletionTool> chatCompletionTools;

    public OpenAIServiceAdapter(String apiKey) {
        this.client = OpenAIClientFactory.getClient(apiKey);
    }

	@SuppressWarnings("rawtypes")
	public void functions(List<TFunction> functions) {
		this.chatCompletionTools = functions.stream()
				.map(f -> {					
					try {
						// Generate JSON Schema from Person POJO
						
						JsonSchema jsonSchema = schemaGen.generateSchema(f.getModel());

						// Convert schema to Map<String, Object>
						Map<String, Object> schemaMap = mapper.convertValue(jsonSchema, new TypeReference<Map<String, Object>>() {});

						// Manually adjust to match original constraints (Jackson doesn't auto-set these)
						schemaMap.put("additionalProperties", false);

						// Build FunctionParameters from the schema map
						FunctionParameters.Builder paramsBuilder = FunctionParameters.builder();
						schemaMap.forEach((key, value) -> {
						    paramsBuilder.putAdditionalProperty(key, toJsonValue(value));
						});
						
						return ChatCompletionTool.ofFunction(
							ChatCompletionFunctionTool.builder()
						    .function(FunctionDefinition.builder()
						            .name(f.getName())
						            .description(f.getDescription())
						            .parameters(paramsBuilder.build())
						            .build())
						    .build());
					} catch (Exception e) {
						throw new RuntimeException("Erro ao gerar schema para função: " + f.getName(), e);
					}})
				.toList();
	}
	
	public ChatCompletion sendToolCallResult(String model, List<ChatCompletionMessageParam> messages, String functionId, ToolCallResult toolCallResult) {
        try {
        	
        	ChatCompletionToolMessageParam resultParam = ChatCompletionToolMessageParam.builder()
					.toolCallId(functionId)
					.contentAsJson(mapper.writeValueAsString(toolCallResult))
					.build();
        	
            builder = (builder==null) 
            		? ChatCompletionCreateParams.builder()
            			.model(model)                    
            			.messages(messages)
		                .temperature(1.0)
		                .maxCompletionTokens(maxTokens)
		                .tools(chatCompletionTools)
		                .addMessage(resultParam)
		                : builder.addMessage(resultParam);
            
            
            ChatCompletion completion = client.chat().completions().create(builder.build());
        	builder.addMessage(completion.choices().get(0).message());
            return completion;
            
        } catch (Exception e) {
            LOGGER.error("Erro ao chamar OpenAI: {}", e.getMessage());
            throw new RuntimeException("Erro ao chamar OpenAI", e);
        }
    }
    
    public ChatCompletion sendChatRequest(String model, List<ChatCompletionMessageParam> messages) {
        try {
        	if(builder==null)
	        	builder = chatCompletionTools!=null 
	            		? ChatCompletionCreateParams.builder()
	            				.model(model)                    
	            				.messages(messages)
			                    .temperature(1.0)
			                    .maxCompletionTokens(maxTokens)
			                    .tools(chatCompletionTools)
			                    
		                    : ChatCompletionCreateParams.builder()
		        				.model(model)                    
		        				.messages(messages)
			                    .temperature(1.0)
			                    .maxCompletionTokens(maxTokens);
        	ChatCompletion completion = client.chat().completions().create(builder.build());
        	builder.addMessage(completion.choices().get(0).message());
            return completion;
            
        } catch (Exception e) {
            LOGGER.error("Erro ao chamar OpenAI: {}", e.getMessage());
            throw new RuntimeException("Erro ao chamar OpenAI", e);
        }
    }
    
    public ChatCompletionMessageParam buildAssistantMessage(String content) {
        return ChatCompletionMessageParam.ofAssistant(ChatCompletionAssistantMessageParam.builder()
				.content(content).build());
    }

    public ChatCompletionMessageParam buildUserMessage(String content) {
    	//String userName = TedrosContext.getLoggedUser().getName();
    	String userName = "Davis";
        return ChatCompletionMessageParam.ofUser(ChatCompletionUserMessageParam.builder()
        		.name(userName)
				.content(content).build());
    }
    
    public ChatCompletionMessageParam buildSysMessage(String content) {
        return ChatCompletionMessageParam.ofSystem(ChatCompletionSystemMessageParam.builder()
				.content(content).build());
    }
    
    private static JsonValue toJsonValue(Object value) {
        if (value == null) {
            return JsonValue.from(null);
        } else if (value instanceof Map) {
            Map<String, JsonValue> jsonMap = new HashMap<>();
            ((Map<?, ?>) value).forEach((k, v) -> jsonMap.put(k.toString(), toJsonValue(v)));
            return JsonValue.from(jsonMap);
        } else if (value instanceof List) {
            List<JsonValue> jsonList = new ArrayList<>();
            ((List<?>) value).forEach(item -> jsonList.add(toJsonValue(item)));
            return JsonValue.from(jsonList);
        } else if (value instanceof String || value instanceof Number || value instanceof Boolean) {
            return JsonValue.from(value);
        } else {
            throw new IllegalArgumentException("Unsupported type: " + value.getClass());
        }
    }
}
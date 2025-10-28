package org.tedros.ai.openai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.tedros.ai.function.TFunction;
import org.tedros.ai.function.TRequiredProperty;
import org.tedros.ai.openai.model.ToolCallResult;
import org.tedros.util.TLoggerUtil;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import com.openai.client.OpenAIClient;
import com.openai.core.JsonValue;
import com.openai.models.responses.EasyInputMessage;
import com.openai.models.responses.FunctionTool;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCreateParams;
import com.openai.models.responses.ResponseFunctionToolCall;
import com.openai.models.responses.ResponseInputItem;
import com.openai.models.responses.ResponseOutputItem;
import com.openai.models.responses.Tool;

/**
 * Adaptador genérico para criar requisições de chat.
 */
public class OpenAIServiceAdapterOld {

    private static final Logger LOGGER = TLoggerUtil.getLogger(OpenAIServiceAdapterOld.class);
    
    //private static long maxTokens = 2000;
    private static ObjectMapper mapper = new ObjectMapper();
    private static JsonSchemaGenerator schemaGen = new JsonSchemaGenerator(mapper);

    private final OpenAIClient client;
    
    private ResponseCreateParams.Builder builder;
    
    private List<Tool> chatCompletionTools;

    public OpenAIServiceAdapterOld(String apiKey) {
        this.client = OpenAIClientFactory.getClient(apiKey);
    }

	@SuppressWarnings("rawtypes")
	public void functions(List<TFunction> functions) {
		this.chatCompletionTools = functions.stream()
				.map(f -> {					
					try {
						
						FunctionTool.Builder builder = FunctionTool.builder()
								.name(f.getName())
							    .description(f.getDescription())
							    .strict(false);;
						
						// Generate JSON Schema from Person POJO
						
						JsonSchema jsonSchema = schemaGen.generateSchema(f.getModel());

						// Convert schema to Map<String, Object>
						Map<String, Object> schemaMap = mapper.convertValue(jsonSchema, new TypeReference<Map<String, Object>>() {});
						
						// === CORREÇÃO: Coletar 'required' como List<String> ===
		                Map<String, Object> properties = (Map<String, Object>) schemaMap.get("properties");
		                List<String> required = new ArrayList<>();

		                if (properties != null) {
		                    for (String propName : properties.keySet()) {
		                        if (isPropertyRequired(f.getModel(), propName)) {
		                            required.add(propName);
		                        }
		                    }
		                }

		                // Adicionar 'required' apenas se não vazio (OpenAI aceita vazio, mas melhor omitir se desnecessário)
		                if (!required.isEmpty()) {
		                    schemaMap.put("required", required);  // Aqui é List<String>, vira array no JSON
		                }
		                // === FIM DA CORREÇÃO ===

						// Manually adjust to match original constraints (Jackson doesn't auto-set these)
						//schemaMap.put("additionalProperties", true);

						// Build FunctionParameters from the schema map
						FunctionTool.Parameters.Builder paramsBuilder = FunctionTool.Parameters.builder();
						schemaMap.forEach((key, value) -> paramsBuilder.putAdditionalProperty(key, toJsonValue(value)));
						
						builder.parameters(paramsBuilder.build());
						
						return Tool.ofFunction(builder.build());
						
					} catch (Exception e) {
						throw new RuntimeException("Erro ao gerar schema para função: " + f.getName(), e);
					}})
				.toList();
	}
	
	public ResponseOutputItem sendToolCallResult(String model, List<ResponseInputItem> messages, ResponseFunctionToolCall responseFunctionToolCall, ToolCallResult toolCallResult) {
        try {
        	
            builder = (builder==null) 
            		? ResponseCreateParams.builder()
            			.model(model)                    
            			.input(ResponseCreateParams.Input.ofResponse(messages))
		                .temperature(1.0)
		                .tools(chatCompletionTools)
		                : builder.input(ResponseCreateParams.Input.ofResponse(messages));
            
            
            Response response = client.responses().create(builder.build());
            
            return response.output().get(0);
            
        } catch (Exception e) {
            LOGGER.error("Erro ao chamar OpenAI: {}", e.getMessage());
            throw new RuntimeException("Erro ao chamar OpenAI", e);
        }
    }
    
    public ResponseOutputItem sendChatRequest(String model, List<ResponseInputItem> messages) {
        try {
        	if(builder==null) {
	        	builder = chatCompletionTools!=null 
	            		? ResponseCreateParams.builder()
	            				.model(model)                    
	            				.inputOfResponse(messages)
			                    .temperature(1.0)
			                    .tools(chatCompletionTools)
			                    
		                    : ResponseCreateParams.builder()
		        				.model(model)                    
		        				.inputOfResponse(messages)
			                    .temperature(1.0);
        	}else {
        		builder.input(ResponseCreateParams.Input.ofResponse(messages));
        	}
			                    
        	Response responses = client.responses().create(builder.build());
            return responses.output().get(0);
            
        } catch (Exception e) {
            LOGGER.error("Erro ao chamar OpenAI: {}", e.getMessage());
            throw new RuntimeException("Erro ao chamar OpenAI", e);
        }
    }
    
    public ResponseInputItem buildAssistantMessage(String content) {
    	return ResponseInputItem.ofEasyInputMessage(EasyInputMessage.builder()        		
                .role(EasyInputMessage.Role.ASSISTANT)
                .content(content)
                .build());
    }

    public ResponseInputItem buildUserMessage(String content) {
    	return ResponseInputItem.ofEasyInputMessage(EasyInputMessage.builder()        		
                .role(EasyInputMessage.Role.USER)
                .content(content)
                .build());
    }
    
    public ResponseInputItem buildSysMessage(String content) {
        return ResponseInputItem.ofEasyInputMessage(EasyInputMessage.builder()        		
                .role(EasyInputMessage.Role.SYSTEM)
                .content(content)
                .build());
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
    
    private boolean isPropertyRequired(Class<?> modelClass, String fieldName) {
        try {
            java.lang.reflect.Field field = modelClass.getDeclaredField(fieldName);
            if (field.isAnnotationPresent(TRequiredProperty.class)) {
                return true;
            }
        } catch (NoSuchFieldException e) {
            
        }
        return false;
    }
}
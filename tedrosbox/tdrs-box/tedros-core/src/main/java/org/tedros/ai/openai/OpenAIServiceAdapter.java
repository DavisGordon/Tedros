package org.tedros.ai.openai;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
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
public class OpenAIServiceAdapter {

    private static final Logger LOGGER = TLoggerUtil.getLogger(OpenAIServiceAdapter.class);

    private static ObjectMapper mapper = new ObjectMapper();
    private static JsonSchemaGenerator schemaGen = new JsonSchemaGenerator(mapper);

    private final OpenAIClient client;

    private ResponseCreateParams.Builder builder;

    private List<Tool> chatCompletionTools;

    public OpenAIServiceAdapter(String apiKey) {
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
						    .strict(false);
                	
                    // Generate JSON Schema from the model class
                    JsonSchema jsonSchema = schemaGen.generateSchema(f.getModel());

                    // Convert schema to Map<String, Object>
                    Map<String, Object> schemaMap = mapper.convertValue(jsonSchema, new TypeReference<Map<String, Object>>() {});

                    // Recursively add 'required' arrays to the schema and all nested objects
                    addRequiredFieldsRecursively(schemaMap, f.getModel());

                    // Manually adjust to match original constraints
                    schemaMap.put("additionalProperties", false);

                    // Build FunctionParameters from the schema map
                    FunctionTool.Parameters.Builder paramsBuilder = FunctionTool.Parameters.builder();
                    schemaMap.forEach((key, value) -> paramsBuilder.putAdditionalProperty(key, toJsonValue(value)));

                    builder.parameters(paramsBuilder.build());
					
					return Tool.ofFunction(builder.build());

                } catch (Exception e) {
                    throw new RuntimeException("Erro ao gerar schema para função: " + f.getName(), e);
                }
            })
            .toList();
    }

    /**
     * Recursively traverses the schemaMap and adds 'required' arrays based on reflection
     * for the corresponding class and its nested classes.
     *
     * @param schemaMap The current level of the schema map (starting from root).
     * @param currentClass The Class corresponding to the current schema level.
     */
    private void addRequiredFieldsRecursively(Map<String, Object> schemaMap, Class<?> currentClass) {
        if (schemaMap == null || currentClass == null) {
            return;
        }

        // Get properties map for current level
        @SuppressWarnings("unchecked")
        Map<String, Object> properties = (Map<String, Object>) schemaMap.get("properties");
        if (properties == null) {
            return;
        }

        // Collect required fields for this level
        List<String> required = new ArrayList<>();
        for (String propName : properties.keySet()) {
            if (isPropertyRequired(currentClass, propName)) {
                required.add(propName);
            }

            // If this property is an object, recurse into it
            @SuppressWarnings("unchecked")
            Map<String, Object> propSchema = (Map<String, Object>) properties.get(propName);
            String propType = (String) propSchema.get("type");
            if ("object".equals(propType)) {
                // Find the nested class type via reflection
                Class<?> nestedClass = getFieldType(currentClass, propName);
                if (nestedClass != null) {
                    addRequiredFieldsRecursively(propSchema, nestedClass);
                }
            }
            // Note: For arrays of objects, the schema has "items" with "type": "object"
            else if ("array".equals(propType)) {
                @SuppressWarnings("unchecked")
                Map<String, Object> items = (Map<String, Object>) propSchema.get("items");
                if (items != null && "object".equals(items.get("type"))) {
                    // Find the nested class type (assuming it's a collection of a specific type)
                    Class<?> nestedClass = getFieldComponentType(currentClass, propName);
                    if (nestedClass != null) {
                        addRequiredFieldsRecursively(items, nestedClass);
                    }
                }
            }
        }

        // Add 'required' only if there are required fields
        //if (!required.isEmpty()) {
            schemaMap.put("required", required);
        //}
    }

    /**
     * Gets the type of the field by name, handling fields and getters.
     */
    private Class<?> getFieldType(Class<?> clazz, String fieldName) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            return field.getType();
        } catch (NoSuchFieldException e) {
            // Try getter
            try {
                String getterName = "get" + capitalize(fieldName);
                Method method = clazz.getDeclaredMethod(getterName);
                return method.getReturnType();
            } catch (Exception ex) {
                LOGGER.warn("Could not find type for field: " + fieldName);
            }
        }
        return null;
    }

    /**
     * For array or collection fields, gets the component type (e.g., for List<FilterCondition>, returns FilterCondition.class).
     * For simplicity, assumes it's an array or simple type; extend for generics if needed.
     */
    private Class<?> getFieldComponentType(Class<?> clazz, String fieldName) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            Class<?> type = field.getType();
            if (type.isArray()) {
                return type.getComponentType();
            }
            // For collections, you'd need ParameterizedType, but for arrays like String[], it's String.class
            if (String[].class.equals(type)) {  // Adjust based on your types
                return String.class;
            }
            // Add handling for List<T> via ParameterizedType if needed
        } catch (NoSuchFieldException e) {
            // Similar for getter
        }
        return null;
    }

    private String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * Checks if a property is required using @JsonProperty(required = true).
     */
    private boolean isPropertyRequired(Class<?> clazz, String fieldName) {
    	try {
            java.lang.reflect.Field field = clazz.getDeclaredField(fieldName);
            if (field.isAnnotationPresent(TRequiredProperty.class)) {
                return true;
            }
        } catch (NoSuchFieldException e) {
            
        }
        return false;
    }

    public ResponseOutputItem sendToolCallResult(String model, List<ResponseInputItem> messages, ResponseFunctionToolCall responseFunctionToolCall, ToolCallResult toolCallResult) {
        try {
            builder = (builder == null)
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
            if (builder == null)
                builder = chatCompletionTools != null
                    ? ResponseCreateParams.builder()
                        .model(model)
                        .input(ResponseCreateParams.Input.ofResponse(messages))  // Corrigido: inputOfResponse -> Input.ofResponse
                        .temperature(1.0)
                        .tools(chatCompletionTools)
                    : ResponseCreateParams.builder()
                        .model(model)
                        .input(ResponseCreateParams.Input.ofResponse(messages))
                        .temperature(1.0);

            Response response = client.responses().create(builder.build());
            return response.output().get(0);

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
}
package org.tedros.ai.service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.tedros.ai.function.TRequiredProperty;

import com.openai.core.JsonValue;

public class AiHelper {
	
	private static final Logger LOGGER = org.tedros.util.TLoggerUtil.getLogger(AiHelper.class);
	
	private AiHelper() {
		
	}
	
	/**
     * Recursively traverses the schemaMap and adds 'required' arrays based on reflection
     * for the corresponding class and its nested classes.
     *
     * @param schemaMap The current level of the schema map (starting from root).
     * @param currentClass The Class corresponding to the current schema level.
     */
    public static void addRequiredFieldsRecursively(Map<String, Object> schemaMap, Class<?> currentClass) {
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

        schemaMap.put("required", required);
        
    }

    /**
     * Gets the type of the field by name, handling fields and getters.
     */
    private static Class<?> getFieldType(Class<?> clazz, String fieldName) {
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
    private static Class<?> getFieldComponentType(Class<?> clazz, String fieldName) {
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

    private static String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * Checks if a property is required using @JsonProperty(required = true).
     */
    private static boolean isPropertyRequired(Class<?> clazz, String fieldName) {
    	try {
            java.lang.reflect.Field field = clazz.getDeclaredField(fieldName);
            if (field.isAnnotationPresent(TRequiredProperty.class)) {
                return true;
            }
        } catch (NoSuchFieldException e) {
            
        }
        return false;
    } 

    public static JsonValue toJsonValue(Object value) {
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
	
	public static Map<String, Integer> buildModelContextLengths(){
	
		Map<String, Integer> map = new HashMap<>();
		map.put("grok-4-fast-reasoning", 2_000_000);
		map.put("grok-4-fast-non-reasoning", 2_000_000);
		map.put("grok-4-1-fast-reasoning", 2_000_000);
		map.put("grok-4-1-fast-non-reasoning", 2_000_000);
		map.put("grok-code-fast-1", 256_000);
		
		// === GPT-5 Series (200k context) ===
	    map.put("gpt-5", 200_000);
	    map.put("gpt-5-mini", 200_000);
	    map.put("gpt-5-nano", 200_000);
	    map.put("gpt-5-2025-08-07", 200_000);
	    map.put("gpt-5-mini-2025-08-07", 200_000);
	    map.put("gpt-5-nano-2025-08-07", 200_000);
	    map.put("gpt-5-chat-latest", 200_000);

	    // === GPT-4.1 Series (128k context) ===
	    map.put("gpt-4.1", 128_000);
	    map.put("gpt-4.1-mini", 128_000);
	    map.put("gpt-4.1-nano", 128_000);
	    map.put("gpt-4.1-2025-04-14", 128_000);
	    map.put("gpt-4.1-mini-2025-04-14", 128_000);
	    map.put("gpt-4.1-nano-2025-04-14", 128_000);

	    // === o4-mini (128k) ===
	    map.put("o4-mini", 128_000);
	    map.put("o4-mini-2025-04-16", 128_000);

	    // === o3 Series (200k context) ===
	    map.put("o3", 200_000);
	    map.put("o3-2025-04-16", 200_000);
	    map.put("o3-mini", 200_000);
	    map.put("o3-mini-2025-01-31", 200_000);

	    // === o1 Series (128k context oficial, apesar de rumores de 200k interno) ===
	    map.put("o1", 128_000);
	    map.put("o1-2024-12-17", 128_000);
	    map.put("o1-preview", 128_000);
	    map.put("o1-preview-2024-09-12", 128_000);
	    map.put("o1-mini", 128_000);
	    map.put("o1-mini-2024-09-12", 128_000);

	    // === GPT-4o Series (128k context) ===
	    map.put("gpt-4o", 128_000);
	    map.put("gpt-4o-2024-11-20", 128_000);
	    map.put("gpt-4o-2024-08-06", 128_000);
	    map.put("gpt-4o-2024-05-13", 128_000);
	    map.put("gpt-4o-audio-preview", 128_000);
	    map.put("gpt-4o-audio-preview-2024-10-01", 128_000);
	    map.put("gpt-4o-audio-preview-2024-12-17", 128_000);
	    map.put("gpt-4o-audio-preview-2025-06-03", 128_000);
	    map.put("gpt-4o-mini-audio-preview", 128_000);
	    map.put("gpt-4o-mini-audio-preview-2024-12-17", 128_000);
	    map.put("gpt-4o-search-preview", 128_000);
	    map.put("gpt-4o-mini-search-preview", 128_000);
	    map.put("gpt-4o-search-preview-2025-03-11", 128_000);
	    map.put("gpt-4o-mini-search-preview-2025-03-11", 128_000);
	    map.put("chatgpt-4o-latest", 128_000);

	    // === GPT-4o-mini Series (128k) ===
	    map.put("gpt-4o-mini", 128_000);
	    map.put("gpt-4o-mini-2024-07-18", 128_000);

	    // === GPT-4 Turbo & Legacy (128k ou menos) ===
	    map.put("gpt-4-turbo", 128_000);
	    map.put("gpt-4-turbo-2024-04-09", 128_000);
	    map.put("gpt-4-0125-preview", 128_000);
	    map.put("gpt-4-turbo-preview", 128_000);
	    map.put("gpt-4-1106-preview", 128_000);
	    map.put("gpt-4-vision-preview", 128_000);

	    // === GPT-4 Classic (8k ou 32k) ===
	    map.put("gpt-4", 8_192);
	    map.put("gpt-4-0314", 8_192);
	    map.put("gpt-4-0613", 8_192);
	    map.put("gpt-4-32k", 32_768);
	    map.put("gpt-4-32k-0314", 32_768);
	    map.put("gpt-4-32k-0613", 32_768);

	    // === GPT-3.5 Turbo (16k max) ===
	    map.put("gpt-3.5-turbo", 16_385);
	    map.put("gpt-3.5-turbo-16k", 16_385);
	    map.put("gpt-3.5-turbo-0301", 4_096);
	    map.put("gpt-3.5-turbo-0613", 16_385);
	    map.put("gpt-3.5-turbo-1106", 16_385);
	    map.put("gpt-3.5-turbo-0125", 16_385);
	    map.put("gpt-3.5-turbo-16k-0613", 16_385);

	    // === Outros ===
	    map.put("codex-mini-latest", 8_192); // estimado
	    
	    return map;
	}
	
	

}

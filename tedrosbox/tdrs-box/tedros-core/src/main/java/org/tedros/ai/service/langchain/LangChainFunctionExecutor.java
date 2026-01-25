package org.tedros.ai.service.langchain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.tedros.ai.function.TFunction;
import org.tedros.ai.openai.model.ToolCallResult;
import org.tedros.util.TLoggerUtil;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;

import dev.langchain4j.agent.tool.ToolExecutionRequest;
import dev.langchain4j.agent.tool.ToolSpecification;
import dev.langchain4j.model.chat.request.json.JsonArraySchema;
import dev.langchain4j.model.chat.request.json.JsonBooleanSchema;
import dev.langchain4j.model.chat.request.json.JsonIntegerSchema;
import dev.langchain4j.model.chat.request.json.JsonNumberSchema;
import dev.langchain4j.model.chat.request.json.JsonObjectSchema;
import dev.langchain4j.model.chat.request.json.JsonSchemaElement;
import dev.langchain4j.model.chat.request.json.JsonStringSchema;

/**
 * Executor de funções adaptado para LangChain4j.
 * Responsável por converter TFunction para ToolSpecification e executar
 * callbacks.
 */
public class LangChainFunctionExecutor {

    private static final Logger LOGGER = TLoggerUtil.getLogger(LangChainFunctionExecutor.class);
    private final Map<String, TFunction<?>> functions = new HashMap<>();
    private final ObjectMapper mapper = new ObjectMapper();
    private final JsonSchemaGenerator schemaGen = new JsonSchemaGenerator(mapper);

    public LangChainFunctionExecutor(List<TFunction<?>> fns) {
        if (fns != null) {
            for (TFunction<?> fn : fns) {
                functions.put(fn.getName(), fn);
            }
        }
    }

    public Collection<ToolSpecification> getToolSpecifications() {
        return functions.values().stream()
                .map(this::toToolSpecification)
                .collect(Collectors.toList());
    }

    private ToolSpecification toToolSpecification(TFunction<?> fn) {
        return ToolSpecification.builder()
                .name(fn.getName())
                .description(fn.getDescription())
                .parameters(generateJsonSchema(fn.getModel()))
                .build();
    }

    /**
     * Gera o JsonObjectSchema do LangChain4j baseado no modelo de dados da função.
     * Utiliza reflexão similar ao AiHelper original, mas mapeando para objetos
     * LangChain4j.
     */
    private JsonObjectSchema generateJsonSchema(Class<?> modelClass) {
        if (modelClass == null) {
            return JsonObjectSchema.builder().build();
        }

        try {
            // Usa o gerador do Jackson para pegar a estrutura básica
            JsonSchema jsonSchema = schemaGen.generateSchema(modelClass);

            // Converte para mapa para facilitar introspecção se necessário,
            // mas aqui vamos construir manual based on reflection para garantir
            // compatibilidade
            // com o que o LangChain4j espera (JsonObjectSchema)

            JsonObjectSchema.Builder builder = JsonObjectSchema.builder();

            // Reflexão simples nos campos para popular properties
            // Nota: Isso é uma simplificação. Para suporte completo a anotações complexas
            // do Jackson, seria ideal usar uma lib que converte Jackson Schema -> LangChain
            // Schema
            // Mas seguindo o padrão do AiHelper original:

            java.lang.reflect.Field[] fields = modelClass.getDeclaredFields();
            for (java.lang.reflect.Field field : fields) {
                String name = field.getName();
                java.lang.reflect.Type type = field.getGenericType(); // Use GenericType

                builder.addProperty(name, mapTypeToJsonSchemaElement(type));

                if (isReq(field)) {
                    builder.required(name);
                }
            }

            return builder.build();

        } catch (Exception e) {
            LOGGER.error("Erro ao gerar schema para classe {}", modelClass.getName(), e);
            return JsonObjectSchema.builder().build();
        }
    }

    private boolean isReq(java.lang.reflect.Field field) {
        try {
            return field.isAnnotationPresent(org.tedros.ai.function.TRequiredProperty.class);
        } catch (Exception e) {
            return false;
        }
    }

    private JsonSchemaElement mapTypeToJsonSchemaElement(java.lang.reflect.Type type) {
        Class<?> rawClass = null;

        if (type instanceof Class<?>) {
            rawClass = (Class<?>) type;
        } else if (type instanceof java.lang.reflect.ParameterizedType) {
            rawClass = (Class<?>) ((java.lang.reflect.ParameterizedType) type).getRawType();
        }

        if (rawClass == null) {
            return JsonObjectSchema.builder().build();
        }

        if (String.class.isAssignableFrom(rawClass) || rawClass.isEnum()
                || Date.class.isAssignableFrom(rawClass) || LocalDate.class.isAssignableFrom(rawClass)
                || LocalDateTime.class.isAssignableFrom(rawClass) || LocalTime.class.isAssignableFrom(rawClass)) {
            return JsonStringSchema.builder().build();
        } else if (Integer.class.isAssignableFrom(rawClass) || int.class.isAssignableFrom(rawClass)
                || Long.class.isAssignableFrom(rawClass) || long.class.isAssignableFrom(rawClass)) {
            return JsonIntegerSchema.builder().build();
        } else if (Double.class.isAssignableFrom(rawClass) || double.class.isAssignableFrom(rawClass)
                || Float.class.isAssignableFrom(rawClass) || float.class.isAssignableFrom(rawClass)) {
            return JsonNumberSchema.builder().build();
        } else if (Boolean.class.isAssignableFrom(rawClass) || boolean.class.isAssignableFrom(rawClass)) {
            return JsonBooleanSchema.builder().build();
        } else if (List.class.isAssignableFrom(rawClass) || rawClass.isArray()) {
            JsonSchemaElement itemSchema = JsonObjectSchema.builder().build(); // Default fallback

            // Try to extract generic type for List
            if (type instanceof java.lang.reflect.ParameterizedType) {
                java.lang.reflect.Type[] typeArguments = ((java.lang.reflect.ParameterizedType) type)
                        .getActualTypeArguments();
                if (typeArguments != null && typeArguments.length > 0) {
                    itemSchema = mapTypeToJsonSchemaElement(typeArguments[0]);
                }
            } else if (rawClass.isArray()) {
                itemSchema = mapTypeToJsonSchemaElement(rawClass.getComponentType());
            }

            return JsonArraySchema.builder().items(itemSchema).build();
        } else {
            // It's a complex object, we must recurse to define properties
            JsonObjectSchema.Builder builder = JsonObjectSchema.builder();
            // Basic recursion protection/limit could be added here if needed,
            // but for simple DTOs just inspecting declared fields is usually enough.

            try {
                // Determine the class to inspect
                Class<?> targetClass = rawClass;
                // Avoid recursing into java internal classes (except List/Map handled above)
                if (targetClass.getName().startsWith("java.")) {
                    return JsonObjectSchema.builder().build();
                }

                java.lang.reflect.Field[] fields = targetClass.getDeclaredFields();
                for (java.lang.reflect.Field field : fields) {
                    // Skip static or transient if desired, but here we include everything basically
                    if (java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
                        continue;
                    }

                    String name = field.getName();
                    java.lang.reflect.Type fieldType = field.getGenericType();

                    builder.addProperty(name, mapTypeToJsonSchemaElement(fieldType));

                    if (isReq(field)) {
                        builder.required(name);
                    }
                }
            } catch (Exception e) {
                // If reflection fails, return empty object
                return JsonObjectSchema.builder().build();
            }

            return builder.build();
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Optional<ToolCallResult> execute(ToolExecutionRequest request) {
        String name = request.name();
        String argumentsJson = request.arguments();

        TFunction<?> fn = functions.get(name);
        if (fn == null) {
            LOGGER.warn("Função não encontrada: {}", name);
            return Optional.empty();
        }

        try {
            LOGGER.info("Executando tool: {}", name);
            Object arg = mapper.readValue(argumentsJson, fn.getModel());
            Function cb = fn.getCallback();
            Object result = cb.apply(arg);

            if (result instanceof ToolCallResult tcr) {
                return Optional.of(tcr);
            }

            return Optional.of(ToolCallResult.builder()
                    .result(Map.of("data", result))
                    .revertToTheAIModelInCaseOfSuccess(fn.itShouldRevertToTheAIModelInCaseOfSuccess())
                    .build());

        } catch (Exception e) {
            LOGGER.error("Erro executando função {}: {}", name, e.getMessage(), e);
            return Optional.of(ToolCallResult.builder()
                    .result(Map.of(
                            "status", "error",
                            "error_message", e.getMessage()))
                    .revertToTheAIModelInCaseOfSuccess(fn.itShouldRevertToTheAIModelInCaseOfSuccess())
                    .build());
        }
    }
}

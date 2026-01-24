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
                Class<?> type = field.getType();

                builder.addProperty(name, mapTypeToJsonSchemaElement(type));

                // Verifica @TRequiredProperty ou lógica similar se existir
                // No original AiHelper.isPropertyRequired usa TRequiredProperty
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

    private JsonSchemaElement mapTypeToJsonSchemaElement(Class<?> type) {
        if (String.class.isAssignableFrom(type) || type.isEnum()
                || Date.class.isAssignableFrom(type) || LocalDate.class.isAssignableFrom(type)
                || LocalDateTime.class.isAssignableFrom(type) || LocalTime.class.isAssignableFrom(type)) {
            return JsonStringSchema.builder().build();
        } else if (Integer.class.isAssignableFrom(type) || int.class.isAssignableFrom(type)
                || Long.class.isAssignableFrom(type) || long.class.isAssignableFrom(type)) {
            return JsonIntegerSchema.builder().build();
        } else if (Double.class.isAssignableFrom(type) || double.class.isAssignableFrom(type)
                || Float.class.isAssignableFrom(type) || float.class.isAssignableFrom(type)) {
            return JsonNumberSchema.builder().build();
        } else if (Boolean.class.isAssignableFrom(type) || boolean.class.isAssignableFrom(type)) {
            return JsonBooleanSchema.builder().build();
        } else if (List.class.isAssignableFrom(type) || type.isArray()) {
            // Simplificação para array, idealmente precisaria do tipo genérico
            return  JsonArraySchema.builder().build();
        } else {
            // Fallback para object ou string
            return JsonObjectSchema.builder().build();
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

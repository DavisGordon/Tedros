package org.tedros.ai.openai;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import org.slf4j.Logger;
import org.tedros.ai.function.TFunction;
import org.tedros.ai.openai.model.ToolCallResult;
import org.tedros.ai.openai.model.ToolError;
import org.tedros.util.TLoggerUtil;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Executor de funções para integração com o SDK oficial.
 */
public class OpenAIFunctionExecutor {

    private static final Logger LOGGER = TLoggerUtil.getLogger(OpenAIFunctionExecutor.class);
    private final Map<String, TFunction<?>> functions = new HashMap<>();
    private final ObjectMapper mapper = new ObjectMapper();

    @SafeVarargs
    public OpenAIFunctionExecutor(TFunction<?>... fns) {
        for (TFunction<?> fn : fns) {
            functions.put(fn.getName(), fn);
        }
    }

    public Set<String> getFunctionNames() {
        return functions.keySet();
    }

    public Optional<ToolCallResult> execute(String name, String argumentsJson) {
        TFunction<?> fn = functions.get(name);
        if (fn == null)
            return Optional.empty();

        try {
            Object arg = mapper.readValue(argumentsJson, fn.getModel());
            Function<Object, Object> cb = (Function<Object, Object>) fn.getCallback();
            Object result = cb.apply(arg);
            return Optional.of(new ToolCallResult(name, result));
        } catch (Exception e) {
            LOGGER.error("Erro executando função {}: {}", name, e.getMessage());
            return Optional.of(new ToolCallResult(name, new ToolError(name, e.getMessage())));
        }
    }
}

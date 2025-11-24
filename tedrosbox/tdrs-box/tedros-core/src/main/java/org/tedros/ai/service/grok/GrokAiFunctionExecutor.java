package org.tedros.ai.service.grok;

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
import com.openai.models.chat.completions.ChatCompletionMessageFunctionToolCall;

/**
 * Executor de funções para integração com o SDK oficial.
 */
public class GrokAiFunctionExecutor {

    private static final Logger LOGGER = TLoggerUtil.getLogger(GrokAiFunctionExecutor.class);
    private final Map<String, TFunction<?>> functions = new HashMap<>();
    private final ObjectMapper mapper = new ObjectMapper();

    @SafeVarargs
    public GrokAiFunctionExecutor(TFunction<?>... fns) {
        for (TFunction<?> fn : fns) {
            functions.put(fn.getName(), fn);
        }
    }

    public Set<String> getFunctionNames() {
        return functions.keySet();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public Optional<ToolCallResult> execute(String name, String argumentsJson) {
        TFunction<?> fn = functions.get(name);
        if (fn == null)
            return Optional.empty();

        try {
            Object arg = mapper.readValue(argumentsJson, fn.getModel());
            Function cb = fn.getCallback();
            Object result = cb.apply(arg);
            return result instanceof ToolCallResult tollCallResult?
            		 Optional.of(tollCallResult) : Optional.of(new ToolCallResult(name, result));
        } catch (Exception e) {
            LOGGER.error("Erro executando função {}: {}", name, e.getMessage());
            return Optional.of(new ToolCallResult(name, new ToolError(name, e.getMessage())));
        }
    }
    
    public Optional<ToolCallResult> callFunction(ChatCompletionMessageFunctionToolCall toolCall) {
    	
    	TFunction<?> fn = functions.get(toolCall.function().name());
        if (fn == null)
            return Optional.empty();        
        
        return execute(toolCall.function().name(), toolCall.function().arguments());
    }
}

package org.tedros.ai.openai.model;

/**
 * Resultado de uma execução de função/tool.
 */
public class ToolCallResult {

    private final String name;
    private final Object result;

    public ToolCallResult(String name, Object result) {
        this.name = name;
        this.result = result;
    }

    public String getName() {
        return name;
    }

    public Object getResult() {
        return result;
    }
}

package org.tedros.ai.openai.model;

/**
 * Representa um erro na execução de função/tool.
 */
public class ToolError {

    private final String functionName;
    private final String message;

    public ToolError(String functionName, String message) {
        this.functionName = functionName;
        this.message = message;
    }

    public String getFunctionName() {
        return functionName;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Error in function '" + functionName + "': " + message;
    }
}

package org.tedros.ai.openai.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Representa uma definição de função (tool/function calling) no SDK oficial.
 */
public class FunctionDefinition {

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("parameters")
    private Object parameters;

    public FunctionDefinition(String name, String description, Object parameters) {
        this.name = name;
        this.description = description;
        this.parameters = parameters;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Object getParameters() {
        return parameters;
    }
}

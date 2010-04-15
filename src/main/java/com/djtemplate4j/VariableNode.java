package com.djtemplate4j;

import java.util.Map;

public class VariableNode implements Node {
    private final String variableName;

    public VariableNode(String variableName) {
        this.variableName = variableName;
    }

    public String render(Map<String, Object> context) {
        if (context.containsKey(this.variableName)) {
            return (String) context.get(this.variableName);
        }
        throw new VariableDoesNotExist(this.variableName);
    }
}

package com.djtemplate4j;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class VariableNode implements Node {
    private final String variable;
    private final List<FilterInfo> filters;

    public VariableNode(String variable) {
        this(variable, Collections.<FilterInfo>emptyList());
    }

    public VariableNode(String variable, List<FilterInfo> filters) {
        this.variable = variable;
        this.filters = filters;
    }

    public String render(Map<String, Object> context) {
        return new Variable(this.variable, this.filters).render(context);
    }
}

package com.djtemplate4j;

import java.util.Map;

import static java.util.Collections.unmodifiableMap;

public class Context {
    private final Map<String, Object> variables;
    private final Map<String, Filter> filters;

    public Context(Map<String, Object> variables, Map<String, Filter> filters) {
        this.variables = unmodifiableMap(variables);
        this.filters = unmodifiableMap(filters);
    }

    public Map<String, Object> getVariables() {
        return variables;
    }

    public Map<String, Filter> getFilters() {
        return filters;
    }
}

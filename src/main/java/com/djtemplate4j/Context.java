package com.djtemplate4j;

import com.djtemplate4j.exceptions.FilterDoesNotExist;

import java.util.Collections;
import java.util.Map;

import static java.util.Collections.unmodifiableMap;

public class Context {
    private final Map<String, Object> variables;
    private final Map<String, Class<? extends Filter>> filters;

    public Context(Map<String, Object> variables, Map<String, Class<? extends Filter>> filters) {
        this.variables = unmodifiableMap(variables);
        this.filters = unmodifiableMap(filters);
    }

    public Context(Map<String, Object> variables) {
        this(variables, Collections.<String, Class<? extends Filter>>emptyMap());
    }

    public Map<String, Object> getVariables() {
        return variables;
    }

    public Filter getFilterInstance(FilterInfo filterInfo) {
        // TODO implement filters with parameter support
        if (filters.containsKey(filterInfo.getName())) {
            try {
                return filters.get(filterInfo.getName()).newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        throw new FilterDoesNotExist(filterInfo.getName());
    }
}

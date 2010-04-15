package com.djtemplate4j;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;
import java.util.regex.Pattern;

public class Variable {
    private static final String VARIABLE_LOOKUP_SEPARATOR = ".";
    private static final String VARIABLE_LOOKUP_SEPARATOR_REGEX = Pattern.quote(VARIABLE_LOOKUP_SEPARATOR);

    private final String variableName;
    private boolean complexLookup;
    private static final String NULL = "null";

    public Variable(String variableName) {
        this.variableName = variableName;
        this.complexLookup = variableName.contains(VARIABLE_LOOKUP_SEPARATOR);
    }

    public String render(Map<String, Object> context) {
        if (this.complexLookup) {
            final LinkedList lookupPath = new LinkedList(Arrays.asList(this.variableName.split(VARIABLE_LOOKUP_SEPARATOR_REGEX)));
            return complexResolve(lookupPath, context);
        }
        return simpleLookupOnMap(this.variableName, context);
    }

    private String simpleLookupOnMap(String variableName, Map<String, Object> context) {
        if (context.containsKey(variableName)) {
            final Object value = context.get(variableName);
            return value != null ? value.toString() : NULL;
        }
        throw new VariableDoesNotExist(this.variableName);
    }

    public String complexResolve(LinkedList<String> lookupPath, Map<String, Object> context) {
        if (lookupPath.size() == 1) {
            return simpleLookupOnMap(lookupPath.peek(), context);
        } else {
            String mapKey = lookupPath.pop();
            if (context.containsKey(mapKey)) {
                return complexResolve(lookupPath, (Map<String, Object>) context.get(mapKey));
            }
        }

        throw new VariableDoesNotExist(this.variableName);
    }


}

package com.djtemplate4j;

import com.djtemplate4j.utils.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.*;
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
            final LinkedList<String> lookupPath = new LinkedList<String>(Arrays.asList(this.variableName.split(VARIABLE_LOOKUP_SEPARATOR_REGEX)));
            return complexResolve(lookupPath, context);
        }
        return simpleLookupOnMap(this.variableName, context);
    }

    // specify what is the key and in which context we could not find that key, like django does:
    // TODO django.template.VariableDoesNotExist: Failed lookup for key [name] in u"{'nme': 'The name'}"

    private String simpleLookupOnMap(String stringKey, Map<?, ?> context) {
        final Map<String, Object> stringObjectMap = mapObjKeysToStringKeys(context.keySet());

        if (stringObjectMap.containsKey(stringKey)) {
            final Object objValue = context.get(stringObjectMap.get(stringKey));
            return objToStringOrNull(objValue);
        }

        throw new VariableDoesNotExist(this.variableName);
    }

    public String complexResolve(LinkedList<String> lookupPath, Map<?, ?> context) {
        if (lookupPath.size() == 1) {
            return simpleLookupOnMap(lookupPath.peek(), context);
        } else {
            final String stringKey = lookupPath.pop();
            final Map<String, Object> stringObjectMap = mapObjKeysToStringKeys(context.keySet());

            if (stringObjectMap.containsKey(stringKey)) {
                final Object objValue = context.get(stringObjectMap.get(stringKey));
                if (objValue instanceof Map) {
                    return complexResolve(lookupPath, (Map<?, ?>) objValue);
                } else {
                    return lookupOnObject(lookupPath, objValue);
                }
            }
        }

        throw new VariableDoesNotExist(this.variableName);
    }

    private String lookupOnObject(LinkedList<String> lookupPath, Object objValue) {
        final String propertyName = lookupPath.pop();
        Method getter = ReflectionUtils.lookupGetterOnObject(propertyName, objValue, new String[]{"get"});

        if (getter != null) {
            try {
                final Object result = getter.invoke(objValue);

                if (lookupPath.size() == 0) {
                    return objToStringOrNull(result);
                } else if (result instanceof Map) {
                    return complexResolve(lookupPath, (Map<?, ?>) result);
                } else {
                    return lookupOnObject(lookupPath, result);
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        throw new VariableDoesNotExist(this.variableName);
    }

    private Map<String, Object> mapObjKeysToStringKeys(Set<?> objKeys) {
        Map<String, Object> mapStringKeyToObjKey = new HashMap<String, Object>();
        for (Object objKey : objKeys) {
            mapStringKeyToObjKey.put(objToStringOrNull(objKey), objKey);
        }
        return mapStringKeyToObjKey;
    }

    private String objToStringOrNull(Object obj) {
        return obj != null ? obj.toString() : NULL;
    }
}

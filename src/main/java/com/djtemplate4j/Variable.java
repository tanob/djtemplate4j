package com.djtemplate4j;

import com.djtemplate4j.maybe.Maybe;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static com.djtemplate4j.utils.ObjectUtils.objToStringOrNull;

public class Variable {
    private static final String VARIABLE_LOOKUP_SEPARATOR = ".";
    private static final String VARIABLE_LOOKUP_SEPARATOR_REGEX = Pattern.quote(VARIABLE_LOOKUP_SEPARATOR);

    private final String variableName;
    private boolean complexLookup;
    private VariableLookup mapLookup = new MapVariableLookup();


    public Variable(String variableName) {
        this.variableName = variableName;
        this.complexLookup = variableName.contains(VARIABLE_LOOKUP_SEPARATOR);
    }

    public String render(Map<String, Object> context) {
        return render(context, Arrays.asList(mapLookup, new GetterVariableLookup(), new MethodVariableLookup()));
    }

    public String render(Map<String, Object> context, List<VariableLookup> lookupImpls) {
        final Object objToRender;

        if (this.complexLookup) {
            final LinkedList<String> lookupPath = new LinkedList<String>(Arrays.asList(this.variableName.split(VARIABLE_LOOKUP_SEPARATOR_REGEX)));
            final Object target = simpleLookupOnMap(lookupPath.pop(), context);
            objToRender = complexLookup(lookupPath, target, lookupImpls);
        } else {
            objToRender = simpleLookupOnMap(this.variableName, context);
        }

        return objToStringOrNull(objToRender);
    }

    // specify what is the key and in which context we could not find that key, like django does:
    // TODO django.template.VariableDoesNotExist: Failed lookup for key [name] in u"{'nme': 'The name'}"

    private Object simpleLookupOnMap(String stringKey, Map<?, ?> context) {
        final Maybe<Object> maybeObject = mapLookup.lookup(stringKey, context);

        if (maybeObject.isJust()) {
            return maybeObject.getValue();
        }

        throw new VariableDoesNotExist(this.variableName);
    }

    private Object complexLookup(LinkedList<String> lookupPath, Object target, final List<VariableLookup> lookupImpls) {
        do {
            String lookupName = lookupPath.pop();
            boolean lookupNameFound = false;

            for (VariableLookup lookupImpl : lookupImpls) {
                final Maybe<Object> maybeObject = lookupImpl.lookup(lookupName, target);
                if (maybeObject.isJust()) {
                    target = maybeObject.getValue();
                    lookupNameFound = true;
                    break;
                }
            }

            if (!lookupNameFound) {
                throw new VariableDoesNotExist(this.variableName);
            }

        } while (lookupPath.size() > 0);

        return target;
    }
}

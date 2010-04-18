package com.djtemplate4j;

import com.djtemplate4j.maybe.Maybe;

import java.util.List;

public class ListIntegerIndexVariableLookup implements VariableLookup {
    public Maybe<Object> lookup(String lookupName, Object target) {
        if (target instanceof List) {
            try {
                final Integer index = Integer.valueOf(lookupName);
                return Maybe.Just(((List<?>)target).get(index));
            } catch (Exception e) {
            }
        }

        return Maybe.Nothing();
    }
}

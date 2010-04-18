package com.djtemplate4j;

import com.djtemplate4j.maybe.Maybe;

public interface VariableLookup {
    Maybe<Object> lookup(String lookupName, Object target);
}

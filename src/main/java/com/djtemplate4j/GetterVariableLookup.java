package com.djtemplate4j;

import com.djtemplate4j.maybe.Maybe;
import com.djtemplate4j.utils.ReflectionUtils;

import java.lang.reflect.Method;

public class GetterVariableLookup implements VariableLookup {
    public Maybe<Object> lookup(String lookupName, Object target) {
        Method getter = ReflectionUtils.lookupGetterOnObject(lookupName, target, new String[]{"get"});

        if (getter != null) {
            try {
                return Maybe.Just(getter.invoke(target));
            } catch (Exception e) {
                // TODO: maybe return Either with the exception instead of Maybe?
                throw new RuntimeException(e);
            }
        }

        return Maybe.Nothing();
    }
}

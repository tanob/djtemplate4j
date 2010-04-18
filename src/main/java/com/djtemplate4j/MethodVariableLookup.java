package com.djtemplate4j;

import com.djtemplate4j.maybe.Maybe;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodVariableLookup implements VariableLookup {
    public Maybe<Object> lookup(String lookupName, Object target) {
        try {
            Method method = target.getClass().getMethod(lookupName);
            if (method != null) {
                method.setAccessible(true);
                return Maybe.Just(method.invoke(target));
            }

        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
        }

        return Maybe.Nothing();
    }
}

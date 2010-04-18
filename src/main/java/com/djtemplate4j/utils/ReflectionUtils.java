package com.djtemplate4j.utils;

import java.lang.reflect.Method;

public class ReflectionUtils {
    public static Method lookupGetterOnObject(String propertyName, Object objValue) {
        return lookupGetterOnObject(propertyName, objValue, new String[]{"get", "is", "has"});
    }

    public static Method lookupGetterOnObject(String propertyName, Object objValue, String prefixes[]) {
        final String ucPropertyName = upperCaseFirst(propertyName);

        for (String prefix : prefixes) {
            try {
                final Method method = objValue.getClass().getMethod(prefix + ucPropertyName);
                if (isValidGetter(method))
                    return method;
            } catch (NoSuchMethodException e) {
            }
        }

        return null;
    }

    public static boolean isValidGetter(Method method) {
        return (method.getParameterTypes().length == 0)
                && (!void.class.equals(method.getReturnType()));
    }

    private static String upperCaseFirst(String propertyName) {
        final String upperCaseFirstChar = (propertyName.charAt(0) + "").toUpperCase();

        if (propertyName.length() == 1) {
            return upperCaseFirstChar;
        }

        return upperCaseFirstChar + propertyName.substring(1);
    }
}

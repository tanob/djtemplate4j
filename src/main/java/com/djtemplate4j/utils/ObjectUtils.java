package com.djtemplate4j.utils;

public class ObjectUtils {
    public static final String NULL = "null";

    public static String objToStringOrNull(Object obj) {
        return obj != null ? obj.toString() : NULL;
    }
}

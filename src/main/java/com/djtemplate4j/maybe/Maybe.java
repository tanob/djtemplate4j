package com.djtemplate4j.maybe;

public abstract class Maybe<T> {
    public abstract boolean isJust();
    public abstract boolean isNothing();
    public abstract T getValue();

    public static <T> Maybe<T> Nothing() {
        return new Nothing<T>();
    }

    public static <T> Maybe<T> Just(T value) {
        return new Just<T>(value);
    }
}

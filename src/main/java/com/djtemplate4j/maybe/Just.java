package com.djtemplate4j.maybe;

public class Just<T> extends Maybe<T> {
    private final T value;

    public Just(T value) {
        this.value = value;
    }

    @Override
    public boolean isJust() {
        return true;
    }

    @Override
    public boolean isNothing() {
        return false;
    }

    @Override
    public T getValue() {
        return value;
    }
}

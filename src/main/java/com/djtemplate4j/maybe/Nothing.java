package com.djtemplate4j.maybe;

public class Nothing<T> extends Maybe<T> {
    @Override
    public boolean isJust() {
        return false;
    }

    @Override
    public boolean isNothing() {
        return true;
    }

    @Override
    public T getValue() {
        throw new RuntimeException("Nothing has no value.");
    }
}

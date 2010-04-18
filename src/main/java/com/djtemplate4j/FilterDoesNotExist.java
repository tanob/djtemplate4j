package com.djtemplate4j;

public class FilterDoesNotExist extends RuntimeException {
    public FilterDoesNotExist(String filterName) {
        super(filterName);
    }
}

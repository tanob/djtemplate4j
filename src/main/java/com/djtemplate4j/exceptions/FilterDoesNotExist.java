package com.djtemplate4j.exceptions;

public class FilterDoesNotExist extends RuntimeException {
    public FilterDoesNotExist(String filterName) {
        super(filterName);
    }
}

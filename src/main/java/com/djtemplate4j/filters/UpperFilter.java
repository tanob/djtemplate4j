package com.djtemplate4j.filters;

import com.djtemplate4j.Filter;

public class UpperFilter implements Filter {
    public String filter(String input) {
        return input != null ? input.toUpperCase() : null;
    }
}

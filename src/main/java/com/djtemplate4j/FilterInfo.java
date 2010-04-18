package com.djtemplate4j;

import java.util.List;

public class FilterInfo {
    private final String name;
    private final List<?> params;

    public FilterInfo(String name, List<?> params) {
        this.name = name;
        this.params = params;
    }

    public String getName() {
        return name;
    }

    public List<?> getParams() {
        return params;
    }
}

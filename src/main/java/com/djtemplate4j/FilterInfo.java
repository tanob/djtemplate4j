package com.djtemplate4j;

import java.util.Collections;
import java.util.List;

import static java.util.Collections.unmodifiableList;

public class FilterInfo {
    private final String name;
    private final List<?> params;

    public FilterInfo(String name, List<?> params) {
        this.name = name;
        this.params = unmodifiableList(params);
    }

    public FilterInfo(String name) {
        this(name, Collections.emptyList());
    }

    public String getName() {
        return name;
    }

    public List<?> getParams() {
        return params;
    }
}

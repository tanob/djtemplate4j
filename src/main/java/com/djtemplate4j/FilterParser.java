package com.djtemplate4j;

import java.util.Collections;

public class FilterParser {
    private final String filterPart;

    public FilterParser(String filterPart) {
        this.filterPart = filterPart;
    }

    public FilterInfo getFilterInfo() {
        return new FilterInfo(filterPart, Collections.emptyList());
    }
}

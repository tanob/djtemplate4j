package com.djtemplate4j;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class VariableTokenParser {
    private static final String FILTER_SEPARATOR_REGEX = Pattern.quote("|");

    private final String content;
    private String variable;
    private List<FilterInfo> filters;

    public VariableTokenParser(String content) {
        this.content = content;
        parseContent();
    }

    private void parseContent() {
        final String[] varNameAndFilters = content.split(FILTER_SEPARATOR_REGEX);
        this.variable = varNameAndFilters[0];
        this.filters = new ArrayList<FilterInfo>();

        for (int i = 1; i < varNameAndFilters.length; i++) {
            String filterPart = varNameAndFilters[i];
            filters.add(new FilterParser(filterPart).getFilterInfo());
        }
    }

    public String getVariable() {
        return variable;
    }

    public List<FilterInfo> getFilters() {
        return filters;
    }
}

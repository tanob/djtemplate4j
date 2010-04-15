package com.djtemplate4j;

public class VariableToken implements Token {
    private final String content;

    public VariableToken(String content) {
        this.content = content;
    }

    public String getContents() {
        return content;
    }
}

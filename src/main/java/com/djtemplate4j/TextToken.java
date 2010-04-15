package com.djtemplate4j;

public class TextToken implements Token {
    private final String content;

    public TextToken(String content) {
        this.content = content;
    }

    public String getContents() {
        return content;
    }
}

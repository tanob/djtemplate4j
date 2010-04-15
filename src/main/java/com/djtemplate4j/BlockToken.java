package com.djtemplate4j;

public class BlockToken implements Token {
    private final String content;

    public BlockToken(String content) {
        this.content = content;
    }

    public String getContents() {
        return content;
    }
}

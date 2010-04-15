package com.djtemplate4j;

import java.util.Map;

public class TextNode implements Node {
    private final String contents;

    public TextNode(String contents) {
        this.contents = contents;
    }

    public String render(Map<String, Object> context) {
        return this.contents;
    }
}

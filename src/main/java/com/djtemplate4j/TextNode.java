package com.djtemplate4j;

public class TextNode implements Node {
    private final String contents;

    public TextNode(String contents) {
        this.contents = contents;
    }

    public String render(Context context) {
        return this.contents;
    }
}

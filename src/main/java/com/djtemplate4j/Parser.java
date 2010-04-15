package com.djtemplate4j;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    private final List<Token> tokens;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public List<Node> parse() {
        ArrayList<Node> nodes = new ArrayList<Node>();

        for (Token token : tokens) {
            nodes.add(transformToNode(token));
        }

        return nodes;
    }

    private Node transformToNode(Token token) {
        if (token instanceof TextToken) {
            return new TextNode(token.getContents());
        } else if (token instanceof VariableToken) {
            return new VariableNode(token.getContents());
        }
        return null;
    }
}

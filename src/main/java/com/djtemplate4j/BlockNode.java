package com.djtemplate4j;

import java.util.ArrayList;
import java.util.List;

public class BlockNode implements Node {
    private List<Node> nodes = new ArrayList<Node>();
    private final String name;
    private final String parameters;

    public BlockNode(String contents) {
        if (contents == null) {
            throw new IllegalArgumentException("contents is null");
        }
        String parts[] = contents.split(" ", 2);
        this.name = parts[0];
        this.parameters = parts.length > 1 ? parts[1] : "";
    }

    public String render(Context context) {
        return null; // TODO
    }

    public void add(Node node) {
        nodes.add(node);
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "BlockNode{" +
                "name='" + name + '\'' +
                '}';
    }
}

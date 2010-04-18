package com.djtemplate4j;

import java.util.List;
import java.util.Map;

public class Template {
    private final String template;
    private List<Node> nodes;

    public Template(String template) {
        this.template = template;
    }

    public String render(Context context) {
        if (nodes == null) {
            nodes = parseTemplate();
        }
        return renderTemplate(context);
    }

    private String renderTemplate(Context context) {
        final StringBuffer buffer = new StringBuffer();
        for (Node node : nodes) {
            buffer.append(node.render(context));
        }
        return buffer.toString();
    }

    private List<Node> parseTemplate() {
        final Lexer lexer = new Lexer(this.template);
        final Parser parser = new Parser(lexer.tokens());
        return parser.parse();
    }
}

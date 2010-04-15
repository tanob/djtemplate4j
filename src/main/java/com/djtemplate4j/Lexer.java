package com.djtemplate4j;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
    private final String VARIABLE_START = "{{";
    private final String VARIABLE_END = "}}";
    private final String BLOCK_START = "{%";
    private final String BLOCK_END = "%}";

    private final String VARIABLE_START_PATTERN = Pattern.quote(VARIABLE_START);
    private final String VARIABLE_END_PATTERN = Pattern.quote(VARIABLE_END);
    private final String BLOCK_START_PATTERN = Pattern.quote(BLOCK_START);
    private final String BLOCK_END_PATTERN = Pattern.quote(BLOCK_END);
    private final Pattern pattern = Pattern.compile(String.format("(%s.*?%s|%s.*?%s)",
            VARIABLE_START_PATTERN, VARIABLE_END_PATTERN, BLOCK_START_PATTERN, BLOCK_END_PATTERN));
    
    private final String template;

    public Lexer(String template) {
        if (template == null) {
            throw new IllegalArgumentException("template is null");
        }
        this.template = template;
    }

    public List<Token> tokens() {
        List<Token> tokens = new ArrayList<Token>();
        String _template = this.template;
        int nextStartIndex = 0;

        Matcher matcher = pattern.matcher(_template);

        while (matcher.find()) {
            String beforeMatch = _template.substring(nextStartIndex, matcher.start());
            String theMatch = matcher.group();

            if (!empty(beforeMatch)) {
                tokens.add(new TextToken(beforeMatch));
            }
            if (!empty(theMatch)) {
                tokens.add(createToken(theMatch));
            }

            nextStartIndex = matcher.end();
        }

        if (nextStartIndex < _template.length())
            tokens.add(new TextToken(_template.substring(nextStartIndex)));

        return tokens;
    }

    private Token createToken(String token) {
        if (token.startsWith(VARIABLE_START)) {
            return new VariableToken(extractTokenContent(token, VARIABLE_START, VARIABLE_END));
        }
        else if (token.startsWith(BLOCK_START)) {
            return new BlockToken(extractTokenContent(token, BLOCK_START, BLOCK_END));
        }

        throw new RuntimeException("Oops! Unknown token: "+ token);
    }

    private String extractTokenContent(String token, String tokenStart, String tokenEnd) {
        return token.substring(tokenStart.length(), token.length() - tokenEnd.length()).trim();
    }

    private boolean empty(String str) {
        return str != null && "".equals(str);
    }
}

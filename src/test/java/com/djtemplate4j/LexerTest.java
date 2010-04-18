package com.djtemplate4j;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class LexerTest {
    @Test(expected = IllegalArgumentException.class)
    public void shouldValidateTheInput() throws Exception {
        new Lexer(null);
    }

    @Test
    public void shouldWorkWithEmptyInput() throws Exception {
        Lexer lexer = new Lexer("");

        assertNotNull(lexer.tokens());
        assertEquals(0, lexer.tokens().size());
    }

    @Test
    public void shouldKeepWhiteSpaces() throws Exception {
        Lexer lexer = new Lexer(" \t\n\r\n");
        List<Token> tokens = lexer.tokens();

        assertNotNull(tokens);
        assertEquals(1, tokens.size());
        
        assertTextWithContent(tokens.get(0), " \t\n\r\n");
    }
    
    @Test
    public void shouldReturnAListOfTokens() throws Exception {
        Lexer lexer = new Lexer("Variable: {{ var1 }} {% block %}{{ var2|upper }}{% endblock %}");
        List<Token> tokens = lexer.tokens();

        assertNotNull(tokens);
        assertEquals(6, tokens.size());

        assertTextWithContent(tokens.get(0), "Variable: ");
        assertVariableWithContent(tokens.get(1), "var1");
        assertTextWithContent(tokens.get(2), " ");
        assertBlockWithContent(tokens.get(3), "block");
        assertVariableWithContent(tokens.get(4), "var2|upper");
        assertBlockWithContent(tokens.get(5), "endblock");
    }

    @Test
    public void shouldWorkWithBeginningBlock() throws Exception {
        Lexer lexer = new Lexer("{% block %} text");
        List<Token> tokens = lexer.tokens();

        assertNotNull(tokens);
        assertEquals(2, tokens.size());

        assertBlockWithContent(tokens.get(0), "block");
        assertTextWithContent(tokens.get(1), " text");
    }

    @Test
    public void shouldWorkWithEndingBlock() throws Exception {
        Lexer lexer = new Lexer("text {% block %}");
        List<Token> tokens = lexer.tokens();

        assertNotNull(tokens);
        assertEquals(2, tokens.size());

        assertTextWithContent(tokens.get(0), "text ");
        assertBlockWithContent(tokens.get(1), "block");
    }

    @Test
    public void shouldWorkWithMiddleBlock() throws Exception {
        Lexer lexer = new Lexer("text {% block %} text");
        List<Token> tokens = lexer.tokens();

        assertNotNull(tokens);
        assertEquals(3, tokens.size());

        assertTextWithContent(tokens.get(0), "text ");
        assertBlockWithContent(tokens.get(1), "block");
        assertTextWithContent(tokens.get(2), " text");
    }

    @Test
    public void shouldWorkWithStartingVariable() throws Exception {
        Lexer lexer = new Lexer("{{ var }} text");
        List<Token> tokens = lexer.tokens();

        assertNotNull(tokens);
        assertEquals(2, tokens.size());

        assertVariableWithContent(tokens.get(0), "var");
        assertTextWithContent(tokens.get(1), " text");
    }

    @Test
    public void shouldWorkWithEndingVariable() throws Exception {
        Lexer lexer = new Lexer("text{{ var }}");
        List<Token> tokens = lexer.tokens();

        assertNotNull(tokens);
        assertEquals(2, tokens.size());

        assertTextWithContent(tokens.get(0), "text");
        assertVariableWithContent(tokens.get(1), "var");
    }

    @Test
    public void shouldWorkWithMiddleVariable() throws Exception {
        Lexer lexer = new Lexer("text {{ var }} text");
        List<Token> tokens = lexer.tokens();

        assertNotNull(tokens);
        assertEquals(3, tokens.size());

        assertTextWithContent(tokens.get(0), "text ");
        assertVariableWithContent(tokens.get(1), "var");
        assertTextWithContent(tokens.get(2), " text");
    }

    @Test
    public void shouldWorkWithBlocksAndNewlines() throws Exception {
        Lexer lexer = new Lexer("{% block %}text\nnewline{% endblock %}");
        List<Token> tokens = lexer.tokens();

        assertNotNull(tokens);
        assertEquals(3, tokens.size());
        
        assertBlockWithContent(tokens.get(0), "block");
        assertTextWithContent(tokens.get(1), "text\nnewline");
        assertBlockWithContent(tokens.get(2), "endblock");
    }

    @Test
    public void shouldWorkWithBlockInsideBlock() throws Exception {
        final Lexer lexer = new Lexer("{% block1 %}{% block2 %}some text{% endblock2 %}{% single block %}{% endblock1 %}");
        final List<Token> tokens = lexer.tokens();

        assertNotNull(tokens);
        assertEquals(6, tokens.size());

        assertBlockWithContent(tokens.get(0), "block1");
        assertBlockWithContent(tokens.get(1), "block2");
        assertTextWithContent(tokens.get(2), "some text");
        assertBlockWithContent(tokens.get(3), "endblock2");
        assertBlockWithContent(tokens.get(4), "single block");
        assertBlockWithContent(tokens.get(5), "endblock1");
    }

    private void assertTextWithContent(Token token, String expectedContent) {
        assertTrue(token instanceof TextToken);
        assertEquals(expectedContent, token.getContents());
    }

    private void assertVariableWithContent(Token token, String expectedContent) {
        assertTrue(token instanceof VariableToken);
        assertEquals(expectedContent, token.getContents());
    }

    public void assertBlockWithContent(Token token, String expectedContent) {
        assertTrue(token instanceof BlockToken);
        assertEquals(expectedContent, token.getContents());
    }
}


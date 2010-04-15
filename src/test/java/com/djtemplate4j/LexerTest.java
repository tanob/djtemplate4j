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
        assertTrue(tokens.get(0) instanceof TextToken);
    }
    
    @Test
    public void shouldReturnAListOfTokens() throws Exception {
        Lexer lexer = new Lexer("Variable: {{ var1 }} {% block %}{{ var2|upper }}{% endblock %}");
        List<Token> tokens = lexer.tokens();

        assertNotNull(tokens);
        assertEquals(6, tokens.size());

        assertTrue(tokens.get(0) instanceof TextToken);
        assertEquals("Variable: ", tokens.get(0).getContents());

        assertTrue(tokens.get(1) instanceof VariableToken);
        assertEquals("var1", tokens.get(1).getContents());

        assertTrue(tokens.get(2) instanceof TextToken);
        assertEquals(" ", tokens.get(2).getContents());
        
        assertTrue(tokens.get(3) instanceof BlockToken);
        assertEquals("block", tokens.get(3).getContents());

        assertTrue(tokens.get(4) instanceof VariableToken);
        assertEquals("var2|upper", tokens.get(4).getContents());

        assertTrue(tokens.get(5) instanceof BlockToken);
        assertEquals("endblock", tokens.get(5).getContents());
    }

    @Test
    public void shouldWorkWithBeginningBlock() throws Exception {
        Lexer lexer = new Lexer("{% block %} text");
        List<Token> tokens = lexer.tokens();

        assertNotNull(tokens);
        assertEquals(2, tokens.size());
        assertTrue(tokens.get(0) instanceof BlockToken);
        assertEquals("block", tokens.get(0).getContents());
        assertTrue(tokens.get(1) instanceof TextToken);
        assertEquals(" text", tokens.get(1).getContents());
    }

    @Test
    public void shouldWorkWithEndingBlock() throws Exception {
        Lexer lexer = new Lexer("text {% block %}");
        List<Token> tokens = lexer.tokens();

        assertNotNull(tokens);
        assertEquals(2, tokens.size());
        assertTrue(tokens.get(0) instanceof TextToken);
        assertEquals("text ", tokens.get(0).getContents());
        assertTrue(tokens.get(1) instanceof BlockToken);
        assertEquals("block", tokens.get(1).getContents());
    }

    @Test
    public void shouldWorkWithMiddleBlock() throws Exception {
        Lexer lexer = new Lexer("text {% block %} text");
        List<Token> tokens = lexer.tokens();

        assertNotNull(tokens);
        assertEquals(3, tokens.size());
        assertTrue(tokens.get(0) instanceof TextToken);
        assertEquals("text ", tokens.get(0).getContents());
        assertTrue(tokens.get(1) instanceof BlockToken);
        assertEquals("block", tokens.get(1).getContents());
        assertTrue(tokens.get(2) instanceof TextToken);
        assertEquals(" text", tokens.get(2).getContents());
    }

    @Test
    public void shouldWorkWithStartingVariable() throws Exception {
        Lexer lexer = new Lexer("{{ var }} text");
        List<Token> tokens = lexer.tokens();

        assertNotNull(tokens);
        assertEquals(2, tokens.size());
        assertTrue(tokens.get(0) instanceof VariableToken);
        assertEquals("var", tokens.get(0).getContents());
        assertTrue(tokens.get(1) instanceof TextToken);
        assertEquals(" text", tokens.get(1).getContents());
    }

    @Test
    public void shouldWorkWithEndingVariable() throws Exception {
        Lexer lexer = new Lexer("text{{ var }}");
        List<Token> tokens = lexer.tokens();

        assertNotNull(tokens);
        assertEquals(2, tokens.size());
        assertTrue(tokens.get(0) instanceof TextToken);
        assertEquals("text", tokens.get(0).getContents());
        assertTrue(tokens.get(1) instanceof VariableToken);
        assertEquals("var", tokens.get(1).getContents());
    }

    @Test
    public void shouldWorkWithMiddleVariable() throws Exception {
        Lexer lexer = new Lexer("text {{ var }} text");
        List<Token> tokens = lexer.tokens();

        assertNotNull(tokens);
        assertEquals(3, tokens.size());
        assertTrue(tokens.get(0) instanceof TextToken);
        assertEquals("text ", tokens.get(0).getContents());
        assertTrue(tokens.get(1) instanceof VariableToken);
        assertEquals("var", tokens.get(1).getContents());
        assertTrue(tokens.get(2) instanceof TextToken);
        assertEquals(" text", tokens.get(2).getContents());
    }

    @Test
    public void shouldWorkWithBlocksAndNewlines() throws Exception {
        Lexer lexer = new Lexer("{% block %}text\nnewline{% endblock %}");
        List<Token> tokens = lexer.tokens();

        assertNotNull(tokens);
        assertEquals(3, tokens.size());
        assertTrue(tokens.get(0) instanceof BlockToken);
        assertEquals("block", tokens.get(0).getContents());
        assertTrue(tokens.get(1) instanceof TextToken);
        assertEquals("text\nnewline", tokens.get(1).getContents());
        assertTrue(tokens.get(2) instanceof BlockToken);
        assertEquals("endblock", tokens.get(2).getContents());
    }
}


package com.djtemplate4j;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ParserTest {
    @Test
    public void shouldSupportBlocks() throws Exception {
        final Lexer lexer = new Lexer("{% block %}{% inner block %}{% endblock %}");
        final Parser parser = new Parser(lexer.tokens());

        final List<Node> nodes = parser.parse();

        assertNotNull(nodes);
        assertEquals(1, nodes.size());

        assertTrue(nodes.get(0) instanceof BlockNode);
        assertEquals("block", ((BlockNode)nodes.get(0)).getName());

        assertEquals(1, ((BlockNode) nodes.get(0)).getNodes().size());
    }

    @Test
    public void shouldSupportNestedOpenCloseBlocks() throws Exception {
        final Lexer lexer = new Lexer("{% block1 %}{% block2 %}{% inner block2 %}{% endblock2 %}{% inner block1 %}{% endblock1 %}");
        final Parser parser = new Parser(lexer.tokens());

        final List<Node> nodes = parser.parse();

        assertNotNull(nodes);
        assertEquals(1, nodes.size());

        assertTrue(nodes.get(0) instanceof BlockNode);

        BlockNode block1 = (BlockNode) nodes.get(0);

        assertEquals("block1", block1.getName());
        assertEquals(2, block1.getNodes().size());

        assertTrue(block1.getNodes().get(0) instanceof BlockNode);
        assertTrue(block1.getNodes().get(1) instanceof BlockNode);

        BlockNode block2 = (BlockNode) block1.getNodes().get(0);
        BlockNode innerBlock1 = (BlockNode) block1.getNodes().get(1);

        assertEquals("block2", block2.getName());
        assertEquals("inner", innerBlock1.getName());

        assertEquals(1, block2.getNodes().size());
        assertTrue(block2.getNodes().get(0) instanceof BlockNode);

        BlockNode innerBlock2 = (BlockNode) block2.getNodes().get(0);
        assertEquals("inner", innerBlock2.getName());
    }
}

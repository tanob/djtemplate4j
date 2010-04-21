package com.djtemplate4j;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BlockNodeTest {
    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionIfPassingNullToConstructor() throws Exception {
        new BlockNode(null);
    }

    @Test
    public void shouldSetNameFromContentsPassedToConstructor() throws Exception {
        final BlockNode blockNode1 = new BlockNode("block1");
        assertEquals("block1", blockNode1.getName());

        final BlockNode blockNode2 = new BlockNode("block2 something else");
        assertEquals("block2", blockNode2.getName());
    }
}

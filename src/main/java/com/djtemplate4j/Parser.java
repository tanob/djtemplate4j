package com.djtemplate4j;

import java.util.LinkedList;
import java.util.List;

public class Parser {
    private static final String END_BLOCK_PREFIX = "end";

    private final List<Token> tokens;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public List<Node> parse() {
        final LinkedList<BlockNode> blockStack = new LinkedList<BlockNode>();
        blockStack.push(new BlockNode("ROOT"));

        for (Token token : tokens) {
            final BlockNode current = blockStack.peek();

            if (token instanceof TextToken) {
                current.add(new TextNode(token.getContents()));
            } else if (token instanceof VariableToken) {
                final VariableTokenParser variableTokenParser = new VariableTokenParser(token.getContents());
                current.add(new VariableNode(variableTokenParser.getVariable(), variableTokenParser.getFilters()));
            } else if (token instanceof BlockToken) {
                boolean isCloseBlock = token.getContents().startsWith(END_BLOCK_PREFIX);
                if (!isCloseBlock) {
                    final BlockNode newBlock = new BlockNode(token.getContents());
                    current.add(newBlock);
                    blockStack.push(newBlock);
                } else {
                    final String blockName = token.getContents().substring(END_BLOCK_PREFIX.length());
                    while (!blockName.equals(blockStack.pop().getName())) ;
                }
            }
        }

        // TODO throw exception if blockStack.size != 1

        return blockStack.pop().getNodes();
    }
}

package com.djtemplate4j;

import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class VariableTokenParserTest {
    @Test
    public void shouldParseAVariableThatHasNoFilters() throws Exception {
        final VariableTokenParser parser = new VariableTokenParser("var.name");

        assertEquals("var.name", parser.getVariable());
        assertEquals(Collections.<FilterInfo>emptyList(), parser.getFilters());
    }

    @Test
    public void shouldParseAVariableThatHasAFilterWithoutParams() throws Exception {
        final VariableTokenParser parser = new VariableTokenParser("var.name|upper");

        assertEquals("var.name", parser.getVariable());
        assertEquals(1, parser.getFilters().size());

        final FilterInfo filterInfo = parser.getFilters().get(0);

        assertEquals("upper", filterInfo.getName());
        assertEquals(Collections.<Object>emptyList(), filterInfo.getParams());
    }

    @Test
    public void shouldParseAVariableThatHasMoreThanOneFilterWithoutParams() throws Exception {
        final VariableTokenParser parser = new VariableTokenParser("var.name|upper|escape");

        assertEquals("var.name", parser.getVariable());
        assertEquals(2, parser.getFilters().size());

        final FilterInfo filter1 = parser.getFilters().get(0);

        assertEquals("upper", filter1.getName());
        assertEquals(Collections.<Object>emptyList(), filter1.getParams());

        final FilterInfo filter2 = parser.getFilters().get(1);

        assertEquals("escape", filter2.getName());
        assertEquals(Collections.<Object>emptyList(), filter2.getParams());
    }
}

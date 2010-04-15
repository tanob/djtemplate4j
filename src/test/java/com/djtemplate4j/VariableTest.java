package com.djtemplate4j;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class VariableTest {
    private Map<String, Object> context;

    @Before
    public void setUp() throws Exception {
        context = new HashMap<String, Object>();
    }

    @Test(expected = VariableDoesNotExist.class)
    public void shouldThrowExceptionWhenVariableDoesNotExistInContext() throws Exception {
        final Variable variable = new Variable("name");
        variable.render(context);
    }

    @Test
    public void shouldSupportDirectVariables() throws Exception {
        final Variable variable = new Variable("name");
        context.put("name", "Haskell");
        final String output = variable.render(context);

        assertEquals("Haskell", output);
    }

    @Test
    public void shouldSupportDirectMapLookup() throws Exception {
        final Variable variable = new Variable("person.name");

        Map<String, String> person = new HashMap<String, String>();
        person.put("name", "Haskell");

        context.put("person", person);
        final String output = variable.render(context);

        assertEquals("Haskell", output);
    }

    @Test(expected = VariableDoesNotExist.class)
    public void shouldThrowExceptionIfMapLookupDoesNotFindVariable() throws Exception {
        final Variable variable = new Variable("person.name");
        context.put("person", new HashMap<String, String>());
        variable.render(context);
    }
}

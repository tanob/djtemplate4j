package com.djtemplate4j;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class Variable_MapLookupTest {
    private Map<String, Object> context;

    @Before
    public void setUp() throws Exception {
        context = new HashMap<String, Object>();
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

    @Test
    public void shouldSupportMapKeysThatAreNotString() throws Exception {
        final Variable variable = new Variable("ages.16");
        final HashMap<Integer, String> ages = new HashMap<Integer, String>();
        ages.put(16, "16 years");
        context.put("ages", ages);

        final String output = variable.render(context);

        assertEquals("16 years", output);
    }
}

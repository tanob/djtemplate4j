package com.djtemplate4j;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class Variable_MapLookupTest {
    private Map<String, Object> variables;
    private List<VariableLookup> lookupImpls;
    private Context context;

    @Before
    public void setUp() throws Exception {
        variables = new HashMap<String, Object>();
        context = new Context(variables);
        lookupImpls = new ArrayList<VariableLookup>();
        lookupImpls.add(new MapVariableLookup());
    }
    
    @Test
    public void shouldSupportDirectMapLookup() throws Exception {
        final Variable variable = new Variable("person.name");

        Map<String, String> person = new HashMap<String, String>();
        person.put("name", "Haskell");

        variables.put("person", person);
        final String output = variable.render(context, lookupImpls);

        assertEquals("Haskell", output);
    }

    @Test(expected = VariableDoesNotExist.class)
    public void shouldThrowExceptionIfMapLookupDoesNotFindVariable() throws Exception {
        final Variable variable = new Variable("person.name");
        variables.put("person", new HashMap<String, String>());
        variable.render(context, lookupImpls);
    }

    @Test
    public void shouldSupportMapKeysThatAreNotString() throws Exception {
        final Variable variable = new Variable("ages.16");
        final HashMap<Integer, String> ages = new HashMap<Integer, String>();
        ages.put(16, "16 years");
        variables.put("ages", ages);

        final String output = variable.render(context, lookupImpls);

        assertEquals("16 years", output);
    }
}

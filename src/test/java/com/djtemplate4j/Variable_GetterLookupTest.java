package com.djtemplate4j;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class Variable_GetterLookupTest {
    private Map<String, Object> context;
    private List<VariableLookup> lookupImpls;

    @Before
    public void setUp() throws Exception {
        context = new HashMap<String, Object>();
        lookupImpls = new ArrayList<VariableLookup>();
        lookupImpls.add(new GetterVariableLookup());
    }

    @Test
    public void shouldCallTheGetter() throws Exception {
        final Person person = new Person();
        context.put("person", person);

        final Variable variable = new Variable("person.name");

        final String output = variable.render(context, lookupImpls);

        assertEquals("Haskell", output);
    }
    
    private class Person {
        public String getName() {
            return "Haskell";
        }
    }
}

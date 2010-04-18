package com.djtemplate4j;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class Variable_GetterLookupTest {
    private Map<String, Object> context;

    @Before
    public void setUp() throws Exception {
        context = new HashMap<String, Object>();
    }

    @Test
    public void shouldCallTheGetter() throws Exception {
        final Person person = new Person();
        context.put("person", person);

        final Variable variable = new Variable("person.name");

        final String output = variable.render(context);

        assertEquals("Haskell", output);
    }
    
    private class Person {
        public String getName() {
            return "Haskell";
        }
    }
}

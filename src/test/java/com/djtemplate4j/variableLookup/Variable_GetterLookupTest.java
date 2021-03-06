package com.djtemplate4j.variableLookup;

import com.djtemplate4j.Context;
import com.djtemplate4j.Variable;
import com.djtemplate4j.VariableLookup;
import com.djtemplate4j.exceptions.VariableDoesNotExist;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class Variable_GetterLookupTest {
    private Map<String, Object> variables;
    private List<VariableLookup> lookupImpls;
    private Context context;

    @Before
    public void setUp() throws Exception {
        variables = new HashMap<String, Object>();
        context = new Context(variables);
        lookupImpls = new ArrayList<VariableLookup>();
        lookupImpls.add(new GetterVariableLookup());
    }

    @Test
    public void shouldCallTheGetter() throws Exception {
        final Person person = new Person();
        variables.put("person", person);

        final Variable variable = new Variable("person.name");

        final String output = variable.render(context, lookupImpls);

        assertEquals("Haskell", output);
    }

    @Test(expected = VariableDoesNotExist.class)
    public void shouldThrowVariableDoesNotExistIfTheMethodDoesNotExist() throws Exception {
        final Variable variable = new Variable("person.age");
        variables.put("person", new Person());
        variable.render(context, lookupImpls);
    }

    private class Person {
        public String getName() {
            return "Haskell";
        }
    }
}

package com.djtemplate4j;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class VariableTest {
    private Map<String, Object> variables;
    private Context context;

    @Before
    public void setUp() throws Exception {
        variables = new HashMap<String, Object>();
        context = new Context(variables);
    }

    @Test(expected = VariableDoesNotExist.class)
    public void shouldThrowExceptionWhenVariableDoesNotExistInContext() throws Exception {
        final Variable variable = new Variable("name");
        variable.render(context);
    }

    @Test
    public void shouldSupportDirectVariables() throws Exception {
        final Variable variable = new Variable("name");
        variables.put("name", "Haskell");
        final String output = variable.render(context);

        assertEquals("Haskell", output);
    }

    @Test
    public void shouldRenderNull() throws Exception {
        final Variable variable = new Variable("name");
        variables.put("name", null);
        final String output = variable.render(context);

        assertEquals("null", output);
    }
}

package com.djtemplate4j;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class TemplateTest {
    private HashMap<String, Object> variables;
    private Context context;

    @Before
    public void setUp() throws Exception {
        variables = new HashMap<String, Object>();
        context = new Context(variables, Collections.<String, Filter>emptyMap());
    }

    @Test
    public void shouldRenderAStaticTemplate() throws Exception {
        final Template template = new Template("Hello world!");
        final String output = template.render(context);

        assertEquals("Hello world!", output);
    }

    @Test
    public void shouldRenderAVariable() throws Exception {
        final Template template = new Template("Hello, {{ name }}!");
        variables.put("name", "Haskell Curry");

        final String output = template.render(context);
        assertEquals("Hello, Haskell Curry!", output);
    }

    @Test(expected = VariableDoesNotExist.class)
    public void shouldThrowAnExceptionWhenVariableDoesNotExist() throws Exception {
        final Template template = new Template("Hello, {{ firstName }} {{ lastName }}!");
        variables.put("lastName", "Curry");
        template.render(context);
    }

    @Test
    public void shouldSupportVariablesThatAreMaps() throws Exception {
        final Template template = new Template("Hello, {{ person.firstName }} {{ person.lastName }}!");
        final HashMap<String, String> person = new HashMap<String, String>();
        person.put("firstName", "Haskell");
        person.put("lastName", "Curry");

        variables.put("person", person);
        final String output = template.render(context);

        assertEquals("Hello, Haskell Curry!", output);
    }

    @Test
    public void shouldRenderNull() throws Exception {
        final Template template = new Template("Hello, {{ name }}!");

        variables.put("name", null);
        final String output = template.render(context);

        assertEquals("Hello, null!", output);
    }
}

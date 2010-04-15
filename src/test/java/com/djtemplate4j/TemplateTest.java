package com.djtemplate4j;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class TemplateTest {
    @Test
    public void shouldRenderAStaticTemplate() throws Exception {
        final Template template = new Template("Hello world!");
        final String output = template.render(new HashMap<String, Object>());

        assertEquals("Hello world!", output);
    }

    @Test
    public void shouldRenderAVariable() throws Exception {
        final Template template = new Template("Hello, {{ name }}!");
        final HashMap<String, Object> context = new HashMap<String, Object>();
        context.put("name", "Haskell Curry");
        
        final String output = template.render(context);
        assertEquals("Hello, Haskell Curry!", output);
    }

    @Test(expected = VariableDoesNotExist.class)
    public void shouldThrowAnExceptionWhenVariableDoesNotExist() throws Exception {
        final Template template = new Template("Hello, {{ firstName }} {{ lastName }}!");
        final HashMap<String, Object> context = new HashMap<String, Object>();
        context.put("lastName", "Curry");
        template.render(context);
    }

    @Test
    public void shouldSupportVariablesThatAreMaps() throws Exception {
        final Template template = new Template("Hello, {{ person.firstName }} {{ person.lastName }}!");
        final HashMap<String, String> person = new HashMap<String, String>();
        person.put("firstName", "Haskell");
        person.put("lastName", "Curry");

        final HashMap<String, Object> context = new HashMap<String, Object>();
        context.put("person", person);
        final String output = template.render(context);
        
        assertEquals("Hello, Haskell Curry!", output);
    }
}

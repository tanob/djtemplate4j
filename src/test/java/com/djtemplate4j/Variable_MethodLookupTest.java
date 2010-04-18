package com.djtemplate4j;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class Variable_MethodLookupTest {
    private Map<String, Object> context;
    private List<VariableLookup> lookupImpls;

    @Before
    public void setUp() throws Exception {
        context = new HashMap<String, Object>();
        lookupImpls = new ArrayList<VariableLookup>();
        lookupImpls.add(new MethodVariableLookup());
    }

    @Test
    public void shouldRenderAValueReturnedFromAMethodCall() throws Exception {
        final Variable variable = new Variable("list.size");

        context.put("list", Arrays.asList(1, 2));
        final String output = variable.render(context, lookupImpls);
        
        assertEquals("2", output);
    }

    @Test(expected = VariableDoesNotExist.class)
    public void shouldThrowVariableDoesNotExistWhenMethodDoesNotExist() throws Exception {
        final Variable variable = new Variable("list.somethingThatDoesNotExist");
        
        context.put("list", Arrays.asList(1, 2));
        variable.render(context, lookupImpls);
    }
}

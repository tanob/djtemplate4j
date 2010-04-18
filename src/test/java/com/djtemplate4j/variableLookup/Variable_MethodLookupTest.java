package com.djtemplate4j.variableLookup;

import com.djtemplate4j.Context;
import com.djtemplate4j.Variable;
import com.djtemplate4j.VariableLookup;
import com.djtemplate4j.exceptions.VariableDoesNotExist;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class Variable_MethodLookupTest {
    private Map<String, Object> variables;
    private List<VariableLookup> lookupImpls;
    private Context context;

    @Before
    public void setUp() throws Exception {
        variables = new HashMap<String, Object>();
        context = new Context(variables);
        lookupImpls = new ArrayList<VariableLookup>();
        lookupImpls.add(new MethodVariableLookup());
    }

    @Test
    public void shouldRenderAValueReturnedFromAMethodCall() throws Exception {
        final Variable variable = new Variable("list.size");

        variables.put("list", Arrays.asList(1, 2));
        final String output = variable.render(context, lookupImpls);

        assertEquals("2", output);
    }

    @Test(expected = VariableDoesNotExist.class)
    public void shouldThrowVariableDoesNotExistWhenMethodDoesNotExist() throws Exception {
        final Variable variable = new Variable("list.somethingThatDoesNotExist");

        variables.put("list", Arrays.asList(1, 2));
        variable.render(context, lookupImpls);
    }
}

package com.djtemplate4j;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class Variable_ListIndexLookupTest {
    private Map<String, Object> variables;
    private List<VariableLookup> lookupImpls;
    private Context context;

    @Before
    public void setUp() throws Exception {
        variables = new HashMap<String, Object>();
        context = new Context(variables);
        lookupImpls = new ArrayList<VariableLookup>();
        lookupImpls.add(new ListIntegerIndexVariableLookup());
    }

    @Test
    public void shouldLookupByIndexOnLists() throws Exception {
        final Variable var1 = new Variable("names.0");

        variables.put("names", Arrays.asList("Haskell", "Curry"));
        final String output1 = var1.render(context, lookupImpls);

        assertEquals("Haskell", output1);

        final Variable var2 = new Variable("names.1");
        final String output2 = var2.render(context, lookupImpls);

        assertEquals("Curry", output2);
    }

    @Test(expected = VariableDoesNotExist.class)
    public void shouldThrowVariableDoesNotExistWhenIndexDoesNotExistOnList() throws Exception {
        final Variable variable = new Variable("names.1");
        
        variables.put("names", Collections.<Object>emptyList());
        variable.render(context, lookupImpls);
    }
}

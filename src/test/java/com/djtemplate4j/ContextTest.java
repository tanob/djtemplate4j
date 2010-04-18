package com.djtemplate4j;

import com.djtemplate4j.filters.UpperFilter;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;

import static org.junit.Assert.assertNotNull;

public class ContextTest {
    private HashMap<String, Class<? extends Filter>> filters;

    @Before
    public void setUp() throws Exception {
        filters = new HashMap<String, Class<? extends Filter>>();
        filters.put("upper", UpperFilter.class);
    }

    @Test(expected = FilterDoesNotExist.class)
    public void shouldThrowExceptionIfFilterDoesNotExist() throws Exception {
        final Context context = new Context(Collections.<String, Object>emptyMap());
        context.getFilterInstance(new FilterInfo("upper"));
    }

    @Test
    public void shouldReturnInstanceOfFilter() throws Exception {
        final Context context = new Context(Collections.<String, Object>emptyMap(), filters);
        final Filter filter = context.getFilterInstance(new FilterInfo("upper"));

        assertNotNull(filter);
    }
}

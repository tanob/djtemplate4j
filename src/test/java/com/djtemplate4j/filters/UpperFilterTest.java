package com.djtemplate4j.filters;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class UpperFilterTest {
    private UpperFilter filter;

    @Before
    public void setUp() throws Exception {
        filter = new UpperFilter();
    }

    @Test
    public void shouldReturnNullIfInputIsNull() throws Exception {
        assertNull(filter.filter(null));
    }

    @Test
    public void shouldPutStringInUpperCase() throws Exception {
        assertEquals("UPPERCASE", filter.filter("upperCase"));
    }
}

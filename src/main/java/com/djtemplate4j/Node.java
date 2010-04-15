package com.djtemplate4j;

import java.util.Map;

public interface Node {
    String render(Map<String, Object> context);
}

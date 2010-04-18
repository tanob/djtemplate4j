package com.djtemplate4j;

import com.djtemplate4j.maybe.Maybe;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.djtemplate4j.utils.ObjectUtils.objToStringOrNull;

public class MapVariableLookup implements VariableLookup {
    public Maybe<Object> lookup(String lookupName, Object target) {
        if (target instanceof Map) {
            return lookupOnMap(lookupName, (Map<?, ?>) target);
        }

        return Maybe.Nothing();
    }

    private Maybe<Object> lookupOnMap(String lookupName, Map<?, ?> map) {
        final Map<String, Object> stringObjectMap = mapObjKeysToStringKeys(map.keySet());

        if (stringObjectMap.containsKey(lookupName)) {
            final Object variableValue = map.get(stringObjectMap.get(lookupName));
            return Maybe.Just(variableValue);
        }

        return Maybe.Nothing();
    }

    private Map<String, Object> mapObjKeysToStringKeys(Set<?> objKeys) {
        Map<String, Object> mapStringKeyToObjKey = new HashMap<String, Object>();
        for (Object objKey : objKeys) {
            mapStringKeyToObjKey.put(objToStringOrNull(objKey), objKey);
        }
        return mapStringKeyToObjKey;
    }
}

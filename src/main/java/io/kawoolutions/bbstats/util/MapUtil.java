package io.kawoolutions.bbstats.util;

import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public final class MapUtil {

    private MapUtil() {
    }

    public static <V, K> Map<V, K> invert(Map<K, V> map) {
        return map.entrySet().stream().collect(Collectors.toMap(Entry::getValue, Entry::getKey));
    }
}

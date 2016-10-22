package LRU_cache;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRU_LinkedHashMap {

    private Map<Integer, Integer> map;

    public LRU_LinkedHashMap(int capacity) {
        map = new LinkedHashMap<Integer, Integer>(16, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry eldest) {
                return size() > capacity;
            }
        };
    }

    public int get(int key) {
        return map.getOrDefault(key, -1);
    }

    public void set(int key, int value) {
        map.put(key, value);
    }
}


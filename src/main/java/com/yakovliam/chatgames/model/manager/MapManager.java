package com.yakovliam.chatgames.model.manager;

import java.util.Map;
import java.util.function.Predicate;

public class MapManager<K, V> implements Manager {

    /**
     * Map
     */
    protected final Map<K, V> map;

    /**
     * Map manger
     *
     * @param map map
     */
    public MapManager(Map<K, V> map) {
        this.map = map;
    }

    /**
     * Returns the map
     *
     * @return map
     */
    public Map<K, V> getMap() {
        return map;
    }

    /**
     * Puts an entry into the map
     *
     * @param k k
     * @param v v
     */
    public void put(K k, V v) {
        map.put(k, v);
    }

    /**
     * Returns a V by K
     *
     * @param k k
     * @return v
     */
    public V getByKey(K k) {
        return map.get(k);
    }

    /**
     * Returns an entry by predicate
     *
     * @param predicate predicate
     * @param def       default
     * @return entry
     */
    public Map.Entry<K, V> getByPredicate(Predicate<? super Map.Entry<K, V>> predicate, Map.Entry<K, V> def) {
        return map.entrySet().stream()
                .filter(predicate)
                .findFirst()
                .orElse(def);
    }

}

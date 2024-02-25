package ru.otus.cachehw;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

@Slf4j
public class MyCache<K, V> implements HwCache<K, V> {
    // Надо реализовать эти методы

    private final WeakHashMap<K, V> cache;
    private final List<HwListener<K, V>> listeners;

    public MyCache() {
        cache  = new WeakHashMap<>();
        listeners = new ArrayList<>();
    }

    @Override
    public void put(K key, V value) {
        notify(key, value, "PUT");
        cache.put(key, value);
    }

    @Override
    public void remove(K key) {
        notify(key, cache.get(key), "REMOVE");
        cache.remove(key);
    }

    @Override
    public V get(K key) {
        notify(key, cache.get(key), "GET");
        return cache.get(key);
    }

    private void notify(K key, V value, String PUT) {
        listeners.forEach(listener -> {
            try {
                listener.notify(key, value, PUT);
            }
            catch (Exception e) {
                log.error(this.getClass().getName(), e);
            }
        });
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listeners.remove(listener);
    }
}

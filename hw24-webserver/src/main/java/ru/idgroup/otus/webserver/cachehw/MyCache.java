package ru.idgroup.otus.webserver.cachehw;


import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

public class MyCache<K, V> implements HwCache<K, V> {
    //Надо реализовать эти методы
    private final WeakHashMap<K, V> cashMap = new WeakHashMap<>();
    private List<HwListener<K,V>> listeners = new ArrayList<>();

    @Override
    public void put(K key, V value) {
        cashMap.put(key, value);
        listeners.stream().forEach(kvHwListener -> kvHwListener.notify(key, value, "put"));
    }

    @Override
    public void remove(K key) {
        if (cashMap.containsKey(key)) {
            V value = cashMap.get(key);
            listeners.stream().forEach(kvHwListener -> kvHwListener.notify(key, value, "remove"));
            cashMap.remove(key);
        }
    }

    @Override
    public V get(K key) {
        return cashMap.get(key);
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

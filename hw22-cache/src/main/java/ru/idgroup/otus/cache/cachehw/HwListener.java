package ru.idgroup.otus.cache.cachehw;


public interface HwListener<K, V> {
    void notify(K key, V value, String action);
}

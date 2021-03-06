package com.notjuststudio.threadsauce;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class ConcurrentHashSet<E> extends AbstractSet<E> implements Set<E> {

    private transient ConcurrentHashMap<E,Object> map;

    private static final Object PRESENT = new Object();

    public ConcurrentHashSet() {
        map = new ConcurrentHashMap<>();
    }

    public ConcurrentHashSet(Collection<? extends E> c) {
        map = new ConcurrentHashMap<>(Math.max((int) (c.size()/.75f) + 1, 16));
        addAll(c);
    }

    public ConcurrentHashSet(int initialCapacity, float loadFactor) {
        map = new ConcurrentHashMap<>(initialCapacity, loadFactor);
    }

    public ConcurrentHashSet(int initialCapacity) {
        map = new ConcurrentHashMap<>(initialCapacity);
    }

    public boolean putIfAbsent(E e) {
        return map.putIfAbsent(e, PRESENT) != null;
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return map.containsKey(o);
    }

    @Override
    public Iterator<E> iterator() {
        return map.keySet().iterator();
    }

    @Override
    public boolean add(E e) {
        return map.put(e, PRESENT)==null;
    }

    @Override
    public boolean remove(Object o) {
        return map.remove(o)==PRESENT;
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public void forEach(Consumer<? super E> action) {
        map.forEach((key, value) -> {
            action.accept(key);
        });
    }
}

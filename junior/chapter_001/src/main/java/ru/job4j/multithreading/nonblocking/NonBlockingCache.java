package ru.job4j.multithreading.nonblocking;

import java.util.concurrent.ConcurrentHashMap;

public class NonBlockingCache<T extends Base> {
    private ConcurrentHashMap<Integer, T> map;

    public NonBlockingCache() {
        map = new ConcurrentHashMap<>();
    }

    public void add(T model) {
        map.put(model.getId(), model);
    }

    public void update(T model) {
        map.computeIfPresent(model.getId(), (key, value) -> {
            if (model.getVersion() != value.getVersion()) {
                throw new OptimisticException("invalid versions");
            }
            model.changeVersion();
            return model;
        });
    }

    public void delete(T model) {
        map.remove(model.getId());
    }

    public T get(int id) {
        return map.get(id);
    }
}

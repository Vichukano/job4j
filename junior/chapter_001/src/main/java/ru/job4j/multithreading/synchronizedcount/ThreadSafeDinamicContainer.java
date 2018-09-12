package ru.job4j.multithreading.synchronizedcount;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import ru.job4j.collections.list.Container;
import ru.job4j.collections.list.DinamicContainer;

import java.util.Iterator;

@ThreadSafe
public class ThreadSafeDinamicContainer<E> implements Container<E> {
    @GuardedBy("this")
    private Container<E> dc;

    public ThreadSafeDinamicContainer(Container dc) {
        this.dc = dc;
    }

    @Override
    public synchronized void add(E value) {
        dc.add(value);
    }


    @Override
    public synchronized void delete() {
        dc.delete();
    }

    @Override
    public synchronized E get(int index) {
        return dc.get(index);
    }

    @Override
    public synchronized int getSize() {
        return dc.getSize();
    }

    private synchronized Container<E> copy(Container<E> dc) {
        Container<E> temp = dc;
        return temp;
    }

    @Override
    public synchronized Iterator<E> iterator() {
        return copy(this.dc).iterator();
    }
}

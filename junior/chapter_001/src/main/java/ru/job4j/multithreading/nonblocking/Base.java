package ru.job4j.multithreading.nonblocking;


import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class Base {
    @GuardedBy("this")
    private int id;
    @GuardedBy("this")
    private int version;

    public Base() {
        id++;
    }

    public synchronized int getId() {
        return id;
    }

    public synchronized void setId(int id) {
        this.id = id;
    }

    public synchronized int getVersion() {
        return version;
    }

    public synchronized void setVersion(int version) {
        this.version = version;
    }

    public synchronized void changeVersion() {
        version++;
    }
}

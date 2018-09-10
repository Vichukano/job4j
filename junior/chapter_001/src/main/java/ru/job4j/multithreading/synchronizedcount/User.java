package ru.job4j.multithreading.synchronizedcount;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class User {
    private int id;

    @GuardedBy("this")
    private int amount;

    public User() {

    }

    public User(int id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    public int getId() {
        return this.id;
    }

    public synchronized int getAmount() {
        return this.amount;
    }

    public synchronized void setAmount(int amount) {
        this.amount = amount;
    }
}

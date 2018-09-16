package ru.job4j.multithreading.nonblocking;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class User extends Base {
    @GuardedBy("this")
    private String name;

    public User(String name) {
        super();
        this.name = name;
    }

    public User(User user) {
        setId(user.getId());
        name = user.getName();
        setVersion(user.getVersion());
    }

    public synchronized String getName() {
        return name;
    }

    public synchronized void setName(String name) {
        this.name = name;
    }

    public User copy() {
        return new User(this);
    }
}

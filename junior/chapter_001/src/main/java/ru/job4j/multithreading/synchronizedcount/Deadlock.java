package ru.job4j.multithreading.synchronizedcount;

/**
 * Пример возникновения дедлока.
 */
public class Deadlock {

    public static void main(String[] args) {
        ResourceA resourceA = new ResourceA();
        ResourceB resourceB = new ResourceB();
        resourceA.b = resourceB;
        resourceB.a = resourceA;
        ThreadA threadA = new ThreadA();
        ThreadB threadB = new ThreadB();
        threadA.a = resourceA;
        threadB.b = resourceB;
        new Thread(threadA).start();
        new Thread(threadB).start();
    }
}

class ResourceA {
    ResourceB b;

    public synchronized int getI() {
        return b.printI();
    }

    public synchronized int printI() {
        return 1;
    }
}

class ResourceB {
    ResourceA a;

    public synchronized int getI() {
        return a.printI();
    }

    public synchronized int printI() {
        return 2;
    }
}

class ThreadA implements Runnable {
    ResourceA a;

    @Override
    public void run() {
        System.out.println(a.getI());
    }
}

class ThreadB implements Runnable {
    ResourceB b;

    @Override
    public void run() {
        System.out.println(b.getI());
    }
}

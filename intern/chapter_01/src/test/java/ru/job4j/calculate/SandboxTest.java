package ru.job4j.calculate;

import org.junit.Test;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SandboxTest {

    /**
     * Reverse string example.
     */
    @Test
    public void reverseStringTest() {
        String s = "12345";
        char[] arr = s.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = arr.length - 1; i >= 0; i--) {
            sb.append(arr[i]);
        }
        assertThat(sb.toString(), is("54321"));
    }

    /**
     * When add null key to TreeMap then
     * throw exception.
     */
    @Test(expected = NullPointerException.class)
    public void treeMapTest() {
        Map map = new TreeMap();
        map.put(null, 1);
    }

    /**
     * When add null key to TreeSet then
     * throw exception.
     */
    @Test(expected = NullPointerException.class)
    public void treeSetTest() {
        Set set = new TreeSet();
        set.add(null);
    }

    /**
     * TreeMap store elements with natural ordering
     * by the keys.
     */
    @Test
    public void treeMapComparatorTest() {
        Map<String, String> map = new TreeMap<>();
        map.put("B", "B");
        map.put("A", "A");
        map.put("D", "D");
        Iterator<String> it = map.values().iterator();
        assertThat(it.next(), is("A"));
        assertThat(it.next(), is("B"));
        assertThat(it.next(), is("D"));
    }

    /**
     * TreeSet store elements with natural ordering
     * by values.
     */
    @Test
    public void treeSetComparatorTest() {
        Set<String> set = new TreeSet<>();
        set.add("D");
        set.add("C");
        set.add("A");
        Iterator<String> it = set.iterator();
        assertThat(it.next(), is("A"));
        assertThat(it.next(), is("C"));
        assertThat(it.next(), is("D"));
    }

    /**
     * HashSet can store only one null element.
     */
    @Test
    public void hashSetTest() {
        Set set = new HashSet();
        set.add(null);
        assertThat(set.size(), is(1));
    }
}

/**
 * Thread.join() - поток, у которого вызван этот метод
 * сначала закончит свою работу. Потом выполнится другой поток,
 * в указанном случае main.
 */
class HelloThread implements Runnable {

    @Override
    public void run() {
        System.out.println("1. Hello from HelloThreadClass");
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new HelloThread());
        thread.start();
        thread.join();
        System.out.println("2. Hello from main");
    }
}

class MyThread implements Runnable {

    public void stop() {
        Thread.currentThread().interrupt();
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                System.out.println(Thread.currentThread().isInterrupted());
                System.out.println("Working...");
                Thread.sleep(2000);
                Thread.currentThread().interrupt();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("Thread stopped");
        System.out.println(Thread.currentThread().isInterrupted());
    }

    public static void main(String[] args) throws InterruptedException {
        MyThread thread = new MyThread();
        new Thread(thread).start();
    }
}

/**
 * Порядок инициализации:
 * 1) статический блок
 * 2) анонимный блок
 * 3) конструктор
 */
class Static {

    public Static() {
        System.out.println("From constructor");
    }
    {
        myMethod();
    }

    static {
        staticMethod();
    }

    public static void main(String[] args) {
        Static s = new Static();
        AbstractMap map = new LinkedHashMap();
    }


    public void myMethod() {
        System.out.println("From myMethod");
    }

    public static void staticMethod() {
        System.out.println("From static method");
    }
}

class Animal {
    private String name;

    public Animal(String name) {
        this.name = name;
    }

    public void sayName() {
        System.out.println(this.name);
    }
}

class Cat extends Animal {

    public Cat(String name) {
        super(name);
    }

    public void mew() {
        super.sayName();
    }
}

class Kitty extends Cat {

    public Kitty(String name) {
        super(name);
    }

    public void kity() {
        super.sayName();
    }
}


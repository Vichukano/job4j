package ru.job4j.collections;


import ru.job4j.collections.generic.simplearray.SimpleArray;
import ru.job4j.collections.iterator.even.EvenIterator;
import ru.job4j.collections.list.DinamicContainer;

import java.util.Iterator;

/**
 * Main class.
 */
public class Main {

    public static void main(String[] args) {

        Object o1 = new Object();
        Object o2 = new Object();
        Object o3 = new Object();

        DinamicContainer<Object> dc = new DinamicContainer<>(3);
        dc.add(o1);
        dc.add(o2);
        dc.add(o3);

        Iterator<Object> it = dc.iterator();

        System.out.println(it.next());
        System.out.println(it.next());

        dc.add(o1);
        System.out.println(it.next());





    }
}

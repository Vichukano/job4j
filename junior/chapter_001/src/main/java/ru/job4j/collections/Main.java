package ru.job4j.collections;


import ru.job4j.collections.generic.simplearray.SimpleArray;
import ru.job4j.collections.iterator.even.EvenIterator;
import ru.job4j.collections.list.DinamicContainer;
import ru.job4j.collections.list.DinamicLinkedContainer;

import java.util.Iterator;


/**
 * Main class.
 */
public class Main {

    public static void main(String[] args) {

        Object o1 = new Object();
        Object o2 = new Object();
        Object o3 = new Object();
        Object o4 = new Object();
        Object o6 = new Object();

        DinamicContainer<Object> dq = new DinamicContainer<>(4);
        dq.add(o1);
        dq.add(o2);
        dq.add(o3);
        dq.add(o4);



        Iterator<Object> it = dq.iterator();

        while (it.hasNext()) {
            System.out.println(it.next());
        }














    }
}

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



        DinamicContainer<Integer> dq = new DinamicContainer<>(4);
        dq.add(1);
        dq.add(2);
        dq.add(3);
        dq.add(4);
        dq.deleteFirst();




        System.out.println(dq.get(0));
        System.out.println(dq.get(1));
        System.out.println(dq.get(2));




















    }
}

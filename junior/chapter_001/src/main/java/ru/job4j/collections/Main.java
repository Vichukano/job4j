package ru.job4j.collections;


import ru.job4j.collections.generic.simplearray.SimpleArray;
import ru.job4j.collections.iterator.even.EvenIterator;

import java.util.Iterator;

/**
 * Main class.
 */
public class Main {

    public static void main(String[] args) {

        int[] nums = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        EvenIterator evenIterator = new EvenIterator(nums);

        System.out.println(evenIterator.next());
        System.out.println(evenIterator.next());


    }
}

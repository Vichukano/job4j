package ru.job4j.collections.iterator;

import ru.job4j.collections.iterator.even.EvenIterator;

/**
 * Main class.
 */
public class Main {
    public static void main(String[] args) {
        int[] nums = {2, 4, 5, 1, 10, 1, 6, 6, 8};

        EvenIterator evenIterator = new EvenIterator(nums);

        System.out.println("Count of even numbers = " + evenIterator.getSize());

        while (evenIterator.hasNext()) {
            System.out.println(evenIterator.next());
        }
    }
}

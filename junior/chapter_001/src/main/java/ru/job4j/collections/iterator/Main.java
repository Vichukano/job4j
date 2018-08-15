package ru.job4j.collections.iterator;


import ru.job4j.collections.iterator.convert.IteratorOfIterators;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Main class.
 */
public class Main {
    public static IteratorOfIterators<Integer> it;

    public static void main(String[] args) {

        Iterator<Integer> it1 = Arrays.asList(1, 2, 3, 10).iterator();
        Iterator<Integer> it2 = Arrays.asList(4, 5, 89, 6).iterator();
        Iterator<Integer> it3 = Arrays.asList(7, 8, 9, 0).iterator();
        Iterator<Iterator<Integer>> its = Arrays.asList(it1, it2, it3).iterator();
        IteratorOfIterators iteratorOfIterators = new IteratorOfIterators(it);
        it = iteratorOfIterators.convert(its);

        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }
}

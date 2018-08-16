package ru.job4j.collections;


import ru.job4j.collections.generic.simplearray.SimpleArray;

import java.util.Iterator;

/**
 * Main class.
 */
public class Main {
    private static String[] arr;
    private static Integer[] arr2;
    public static void main(String[] args) {

        SimpleArray<String> sa = new SimpleArray<>(arr, 3);
        SimpleArray<Integer> sa2 = new SimpleArray<Integer>(arr2, 3);


        sa.add("Кот");
        sa.add("Обор");
        sa.add("Мот");


        Iterator<String> it = sa.iterator();

        System.out.println(it.next());
        System.out.println(it.next());
        System.out.println(it.next());
        System.out.println(it.next());








    }
}

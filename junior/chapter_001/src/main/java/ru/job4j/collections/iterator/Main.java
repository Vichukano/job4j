package ru.job4j.collections.iterator;


import ru.job4j.collections.generic.simplearray.SimpleArray;

import java.util.Iterator;

/**
 * Main class.
 */
public class Main {
    private static String[] arr;
    private static Integer[] arr2;
    public static void main(String[] args) {

        SimpleArray<String> sa = new SimpleArray<>(arr, 5);
        SimpleArray<Integer> sa2 = new SimpleArray<Integer>(arr2, 3);


        sa.add("Кот");
        sa.add("Обор");
        sa.add("Мот");
        sa.add("Жот");
        sa.add("чот");

        Iterator<String> it = sa.iterator();

        while (it.hasNext()){
            System.out.println(it.next());
        }







    }
}

package ru.job4j.collections;


import ru.job4j.collections.map.SimpleHashMap;

/**
 * Main class.
 */
public class Main {

    public static void main(String[] args) {

        SimpleHashMap<String, Integer> map = new SimpleHashMap<>();
        map.insert("one", 1);
        map.insert("two", 2);
        map.insert("three", 3);

        System.out.println(map.get("one"));
        System.out.println(map.get("two"));
        System.out.println(map.get("three"));

        System.out.println(map.insert("two", 3));

    }
}

package ru.job4j.collections;

import ru.job4j.collections.trie.SimpleTrie;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Main class.
 */
public class Main {


    public static void main(String[] args) {
        SimpleTrie<Integer> trie = new SimpleTrie<>();
        trie.put("a", 1);
        trie.put("b", 2);
        trie.put("c", 3);
        trie.put("ad", 4);
        trie.put("bor", 5);
        System.out.println(trie.getValue("a"));
        System.out.println(trie.getValue("b"));
        System.out.println(trie.getValue("c"));
        System.out.println(trie.getValue("ad"));
        System.out.println(trie.getValue("bor"));
        //System.out.println(trie.getValue("o"));//NoSuchElementException нет такого ключа.
        System.out.println(trie.getValue("bo"));// value должно быть null.
    }
}

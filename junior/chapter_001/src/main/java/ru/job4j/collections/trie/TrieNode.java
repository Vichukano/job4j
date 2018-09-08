package ru.job4j.collections.trie;

import java.util.*;

/**
 * Класс - узел.
 * В качестве хранимого значения выбран динамический массив индексов.
 */
public class TrieNode {
    Map<Character, TrieNode> children = new HashMap<>();
    List<Integer> index = new ArrayList<>();

    public TrieNode() {

    }

    public TrieNode(int value) {
        this.index.add(value);
    }

}

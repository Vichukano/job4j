package ru.job4j.collections.trie;

import java.util.HashMap;
import java.util.Map;

public class TrieNode<E> {
    Map<Character, TrieNode<E>> children = new HashMap<>();
    E value;
    boolean isLeaf;//Нет реализации.

    public TrieNode(E value) {
        this.value = value;
    }
}

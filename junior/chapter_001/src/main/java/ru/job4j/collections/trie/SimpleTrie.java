package ru.job4j.collections.trie;

import java.util.NoSuchElementException;

public class SimpleTrie<E> {
    private TrieNode<E> root;

    public SimpleTrie() {
        this.root = new TrieNode(null);
    }

    public void put(String key, E value) {
        TrieNode node = root;
        char[] keys = key.toLowerCase().toCharArray();
        for (int i = 0; i < keys.length; i++) {
            if (!node.children.containsKey(keys[i])) {
                node.children.put(keys[i], new TrieNode(value));
            }
            node = (TrieNode) node.children.get(keys[i]);
        }
        node.isLeaf = true;
    }

    public void remove(String s) {

    }

    public TrieNode<E> findNode(String key) {
        TrieNode node = root;
        char[] keys = key.toLowerCase().toCharArray();
        for (int i = 0; i < keys.length; i++) {
            if (node.children.containsKey(keys[i])) {
                node = (TrieNode) node.children.get(keys[i]);
            }
        }
        return node;
    }

    public E getValue(String key) {
        TrieNode<E> node = root;
        char[] keys = key.toLowerCase().toCharArray();
        for (int i = 0; i < key.length(); i++) {
            if (node.children.containsKey(keys[i])) {
                node = node.children.get(keys[i]);
            } else {
                throw new NoSuchElementException();
            }
        }
        return node.value;
    }
}

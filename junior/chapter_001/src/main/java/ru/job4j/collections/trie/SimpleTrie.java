package ru.job4j.collections.trie;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Класс реализует структуру префиксного дерева.
 */
public class SimpleTrie {
    private TrieNode root;

    public SimpleTrie() {
        this.root = new TrieNode();
    }

    /**
     * Метод помещяет ключ и индекс в дерево. Если ключ повторяется, то в массив узла дерева добавляется
     * переданный индекс.
     *
     * @param key   строка.
     * @param value int индекс.
     */
    public void put(String key, int value) {
        TrieNode node = root;
        char[] keys = key.toLowerCase().toCharArray();
        for (int i = 0; i < keys.length; i++) {
            if (!node.children.containsKey(keys[i])) {
                node.children.put(keys[i], new TrieNode());
                if (i == keys.length - 1) {
                    node.children.put(keys[i], new TrieNode(value));
                }
            } else if (i == keys.length - 1) {
                node = node.children.get(keys[i]);
                node.index.add(value);
            }
            node = node.children.get(keys[i]);
        }
    }

    /**
     * Метод проверяет есть ли передаваемое слово в дереве.
     *
     * @param key слово.
     * @return true если есть, иначе false.
     */
    public boolean containsKey(String key) {
        boolean result = false;
        TrieNode node = root;
        char[] keys = key.toLowerCase().toCharArray();
        for (int i = 0; i < keys.length; i++) {
            if (node.children.containsKey(keys[i])) {
                node = node.children.get(keys[i]);
                result = true;
            } else {
                result = false;
                break;
            }
        }
        return result;
    }

    /**
     * Метод возвращает массив индексов переданного слова.
     *
     * @param key слово
     * @return массив индексов.
     */
    public List getByKey(String key) {
        TrieNode node = root;
        char[] keys = key.toLowerCase().toCharArray();
        for (int i = 0; i < key.length(); i++) {
            if (node.children.containsKey(keys[i])) {
                node = node.children.get(keys[i]);
            } else {
                throw new NoSuchElementException();
            }
        }
        return node.index;
    }
}

package ru.job4j.collections.trie;

public class Main {
    public static void main(String[] args) {
        WordIndex wi = new WordIndex();
        wi.loadFile();
        System.out.println(wi.getSt().getByKey("дерево"));
    }
}

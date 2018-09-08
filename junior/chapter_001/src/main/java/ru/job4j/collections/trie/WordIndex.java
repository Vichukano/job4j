package ru.job4j.collections.trie;

import java.util.ArrayList;
import java.util.List;

/**
 * Задача ПЕТЕР - Сервис.
 * Добавить загрузку текста из файла.
 * Рефакторинг.
 */
public class WordIndex {
    private String text;
    private SimpleTrie st;

    public WordIndex() {
        this.st = new SimpleTrie();
        this.text = "Префиксное дерево - структура мама данных, позволяющая хранить ассоциативный массив, "
                + "ключами которого являются строки. "
                + "Представляет мама собой! корневое дерево. Бьют ключами. "
                + "мама аыфаыфаыффффффффффффффффффффф???  авыаываыв"
                + "авывыавыаыв  ываывываы - аыв дерево  мамаавыаы мама";
    }

    public void loadFile() {
        String formattedText = text.replaceAll("[^a-zA-Zа-яА-Я]", " ");
        String[] subString = formattedText.split("\\s+");
        for (int i = 0; i < subString.length; i++) {
            if (!st.containsKey(subString[i])) {
                st.put(subString[i], text.indexOf(subString[i]));
            } else {
                List<Integer> list = new ArrayList<>();
                list.addAll(st.getByKey(subString[i]));
                st.put(subString[i], text.indexOf(subString[i], list.get(list.size() - 1) + subString[i].length()));
            }
        }
    }

    public SimpleTrie getSt() {
        return this.st;
    }
}

package ru.job4j.collections.list;


import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class DinamicLinkedContainer<E> implements Iterable<E> {
    private int size;
    private Node<E> first;
    private int modeCount;
    private int expectedModCount;

    public DinamicLinkedContainer() {
        this.size = 1;
        this.first = null;
    }

    /**
     * Метод вставляет в конец списка данные.
     */
    public void add(E date) {
        Node<E> newLink = this.first;//Установка на первый элемент контейнера.
        if (newLink != null) {//Если контейнер не пустой.
            while (newLink.next != null) {//Доходим до последнего Node в контейнере.
                newLink = newLink.next;
            }
            newLink.next = new Node<>(date);//Создаем следующий Node со значением date.
            size++;//Увеличиваем размер контейнера.
            modeCount++;
        } else {
            this.first = new Node<>(date);//Если контейнер пустой, то создаем заглавный Node со значением date.
        }
    }

    /**
     * Метод получения элемента по индексу.
     *
     * @return - значение Node, соответствующее заданному индексу.
     */
    public E get(int index) {
        Node<E> result = this.first;// Установка положения на первый элемент контейнера.
        for (int i = 0; i < index; i++) {
            result = result.next;// Переходим к следующему Node, пока не дойдем до заданного индекс.
        }
        return result.date;
    }

    /**
     * Метод получения размера коллекции.
     */
    public int getSize() {
        return this.size;
    }

    @Override
    public Iterator<E> iterator() {
        expectedModCount = modeCount;
        return new DinamicLinkedContainerIterator<>();
    }

    /**
     * Класс - итератор для DinamicLinkedContainer.
     *
     * @param <E> - объект коллекции.
     */
    private class DinamicLinkedContainerIterator<E> implements Iterator<E> {
        private int cursor;
        Node<E> current = (Node<E>) first;

        @Override
        public boolean hasNext() {
            if (expectedModCount != modeCount) {
                throw new ConcurrentModificationException();
            }
            if (cursor < size) {
                E date = current.date;
                while (date == null && cursor < size) {
                    current = current.next;
                    date = current.date;
                    cursor++;
                }
            }
            return cursor < size;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            } else {
                cursor++;
                E date = current.date;
                current = current.next;
                return date;
            }
        }
    }

    /**
     * Класс предназначен для хранения данных.
     */
    private static class Node<E> {

        E date;
        Node<E> next;

        private Node(E date) {
            this.date = date;
        }
    }
}

package ru.job4j.collections.list;


import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class DinamicLinkedContainer<E> implements Iterable<E> {
    private int size;
    private Node<E> first;
    private int modeCount;

    public DinamicLinkedContainer() {
        this.first = null;
    }

    /**
     * Метод вставляет в конец списка данные.
     * newLink ссылка на первый элемент контейнера.
     * Если контейнер не пустой идет итерация до последнего Node в контейнере.
     * Создается следующий Node со значением date.
     * Увеличивается размер контейнера.
     * Если контейнер пустой, то создается первый Node со значением date.
     *
     * @param date - объект контейнера.
     */
    public void add(E date) {
        Node<E> newLink = this.first;
        if (newLink != null) {
            while (newLink.next != null) {
                newLink = newLink.next;
            }
            newLink.next = new Node<>(date);
            size++;
            modeCount++;
        } else {
            this.first = new Node<>(date);
            size++;
        }
    }

    /**
     * Метод удалаляет последнее значение контейнера.
     * newLink ссылка на первый элемент контейнера.
     * Если контейнер не пустой идет итерация до предпоследнего Node в контейнере.
     * После установки newLink на предпоследний Node контейнера копирует значение date из следующего Node(последний).
     * Ставит следующий(последний) Node null.
     * Далее уменьщает размер контейнера.
     * Реализованы два условия, если контейнер имеет длинну 1 и 2. В этих случаях не работает newLink.next.next.
     *
     * @return date - удаленный элемент.
     * @throws NoSuchElementException, если контейнер пустой.
     */
    public E remove() {
        Node<E> newLink = this.first;
        if (newLink != null) {
            if (size == 1) {
                E date = first.date;
                first.next = null;
                size--;
                modeCount++;
                return date;
            } else if (size == 2) {
                E date = newLink.next.date;
                newLink.next = null;
                size--;
                modeCount++;
                return date;
            } else {
                while (newLink.next.next != null) {
                    newLink = newLink.next;
                }
                E date = newLink.next.date;
                newLink.next = null;
                size--;
                modeCount++;
                return date;
            }
        } else {
            throw new NoSuchElementException();
        }
    }

    /**
     * Метод удаляет первое значение из колекции.
     * newLink ссылка на первый элемент контейнера.
     * Если контейнер не пустой, удаляется значение - date первого Node.
     * Первый Node смещается на один вправо.
     * Уменьщается размер контейнера.
     *
     * @return date - удаленный элемент.
     * @throws NoSuchElementException, если контейнер пустой.
     */
    public E removeFirst() {
        Node<E> newLink = this.first;
        E date = this.first.date;
        if (newLink != null) {
            newLink.date = null;
            this.first = newLink.next;
            size--;
            modeCount++;
            return date;
        } else {
            throw new NoSuchElementException();
        }

    }

    /**
     * Метод получения элемента по индексу.
     *
     * @param index - индекс элемента.
     * @return - значение Node, соответствующее заданному индексу.
     */
    public E get(int index) {
        if (index >= 0 && index < getSize()) {
            Node<E> result = this.first;
            for (int i = 0; i < index; i++) {
                result = result.next;
            }
            return result.date;
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    /**
     * Метод получения размера коллекции.
     */
    public int getSize() {
        return this.size;
    }

    @Override
    public Iterator<E> iterator() {
        return new DinamicLinkedContainerIterator<>();
    }

    /**
     * Класс - итератор для DinamicLinkedContainer.
     *
     * @param <E> - объект коллекции.
     */
    private class DinamicLinkedContainerIterator<E> implements Iterator<E> {
        private int cursor;
        private int expectedModCount;
        Node<E> current = (Node<E>) first;

        public DinamicLinkedContainerIterator() {
            this.expectedModCount = modeCount;
        }

        @Override
        public boolean hasNext() {
            if (expectedModCount != modeCount) {
                throw new ConcurrentModificationException();
            } else {
                return cursor < size;
            }
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

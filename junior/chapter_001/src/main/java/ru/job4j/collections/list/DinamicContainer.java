package ru.job4j.collections.list;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Класс динамического контейнера.
 * Динамически изменяет размер, если в контейнере нет места.
 *
 * @param <E> - объект контейнера.
 */
public class DinamicContainer<E> implements Iterable<E> {
    private E[] container;
    private int index;
    private int countElements;
    /**
     * Счетчик количества изменений размера контейнера.
     */
    private int modCount;
    private int expectedModCount;

    /**
     * Стандартный конструктор.
     * При инициализации создает массив вместимостью 10 элементов.
     */
    public DinamicContainer() {
        int size = 10;
        this.container = (E[]) new Object[size];
    }

    /**
     * Метод добавляет объект в конец контейнера.
     * Если место в контейнера закончилось, то увеличивает размер контейнера по формуле size = oldSize*3/2 + 1.
     *
     * @param value - значение объекта контейнера.
     */
    public void add(E value) {
        if (index < container.length) {
            container[index++] = value;
            countElements++;
            modCount++;
        } else {
            enlargeSize();
            container[index++] = value;
            countElements++;
            modCount++;
        }
    }

    /**
     * Метод удаляет элемент с конца коллекции.
     */
    public void delete() {
        E[] newContainer = (E[]) new Object[container.length - 1];
        System.arraycopy(container, 0, newContainer, 0, container.length - 1);
        container = newContainer;
    }

    public void deleteFirst() {
        E[] newContainer = (E[]) new Object[container.length - 1];
        System.arraycopy(container, 1, newContainer, 0, container.length - 1);
        container = newContainer;
    }

    /**
     * Метод увеличивает размер контейнера по формуле size = oldSize*3/2 + 1.
     * Метод имеет доступ private, так как вызывается только внутри класса DinamicContainer.
     */
    private void enlargeSize() {
        int newLength = (container.length * 3) / 2 + 1;
        E[] newContainer = (E[]) new Object[newLength];
        System.arraycopy(container, 0, newContainer, 0, container.length);
        container = newContainer;
    }

    /**
     * Метод возвращает значение объекта контейнера по взятому индексу.
     *
     * @param index - индекс объекта контейнера.
     * @return - значение объекта.
     * @throws ArrayIndexOutOfBoundsException, если индекс отрицательный или за пределами размера контейнера.
     */
    public E get(int index) {
        if (index >= 0 && index < container.length) {
            return (E) container[index];
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    /**
     * Метод нужен для тестов.
     *
     * @return - размер контейнера.
     */
    public int getSize() {
        return countElements;
    }

    /**
     * Установка итератора контейнера.
     *
     * @return - вложенный класс - итератор.
     */
    @Override
    public Iterator<E> iterator() {
        /**
         * Переменная записывает количестов изменений размера контейнера.
         */
        expectedModCount = modCount;
        return new DinamicContainerIterator<E>();
    }

    private class DinamicContainerIterator<E> implements Iterator<E> {
        private int cursor;

        /**
         * @return -true or false.
         * @throws ConcurrentModificationException, если после вызова итератора контейнер изменял размер
         *                                          или были добавлены значения.
         */
        @Override
        public boolean hasNext() {
            if (expectedModCount != modCount) {
                throw new ConcurrentModificationException();
            }
            if (cursor < container.length) {
                while (container[cursor] == null && cursor < container.length) {
                    cursor++;
                }
            }
            return cursor < container.length;
        }

        /**
         * Метод возвращает значение объекта коллекции и переходит на слеждующую позицию.
         *
         * @return - значение объекта.
         * @throws NoSuchElementException, если в контейнере больше нет элементов.
         */
        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            } else {
                return (E) container[cursor++];
            }
        }
    }
}

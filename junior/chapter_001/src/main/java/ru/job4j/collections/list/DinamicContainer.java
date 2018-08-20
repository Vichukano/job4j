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
    private int index; //default 0
    /**
     * Счетчик количества изменений размера контейнера.
     */
    private int modCount;
    private int expectedModCount;

    /**
     * Стандартный конструктор.
     *
     * @param size - размер контейнера.
     */
    public DinamicContainer(int size) {
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
            modCount++;
        } else {
            enlargeSize();
            container[index++] = value;
            modCount++;
        }
    }

    public void enlargeSize(){
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
            return container[index];
        } else throw new ArrayIndexOutOfBoundsException();
    }

    /**
     * Метод нужен для тестов.
     *
     * @return - размер контейнера.
     */
    public int getSize() {
        return container.length;
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
        return new DinamicContainerIterator<>();
    }

    private class DinamicContainerIterator<E> implements Iterator<E> {
        private int cursor;

        /**
         * @throws ConcurrentModificationException, если после вызова итератора контейнер изменял размер
         * или были добавлены значения.
         * @return -true or false.
         */
        @Override
        public boolean hasNext() {
            if(expectedModCount != modCount)
                throw new ConcurrentModificationException();
            if (cursor < container.length) {
                while (container[cursor] == null && cursor < container.length) {//Реализован пропуск пустых элементов.
                    cursor++;
                }
            }
            return cursor < container.length;
        }

        /**
         * Метод возвращает значение объекта коллекции и переходит на слеждующую позицию.
         *
         * @return - значение объекта.
         * @throws NoSuchElementException,          если в контейнере больше нет элементов.
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

package ru.job4j.collections.set;

import ru.job4j.collections.list.DinamicContainer;

import java.util.Iterator;

/**
 * Класс реализует композицию DinamicContainer.
 *
 * @param <E> - тип объекта коллекции.
 */
public class SimpleSet<E> implements Iterable<E> {
    private DinamicContainer<E> dc;

    public SimpleSet() {
        this.dc = new DinamicContainer<>(3);
    }

    /**
     * Метод добавляет значение в конец коллекции.
     * Перед добавлением реализована проверка на дублирующиеся значения.
     *
     * @param value - значение добавляемого объекта.
     */
    public void add(E value) {
        if (!checkDuplicate(value)) {
            dc.add(value);
        }
    }

    /**
     * Метод проверяет есть ли в добавляемое значение в колекции.
     *
     * @param value - значение добавляемого объекта.
     * @return - true, если такое значение уже есть в колекции, иначе false.
     */
    public boolean checkDuplicate(E value) {
        boolean result = false;
        if (dc.get(0) != null) {
            for (int i = 0; i < dc.getSize(); i++) {
                if (dc.get(i) == value) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    public E get(int index) {
        return (E) dc.get(index);
    }

    /**
     * Реализация итератора.
     *
     * @return - итератор класса DinamicContainer.
     */
    @Override
    public Iterator<E> iterator() {
        return dc.iterator();
    }
}

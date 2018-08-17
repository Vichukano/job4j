package ru.job4j.collections.generic.store;

import ru.job4j.collections.generic.simplearray.SimpleArray;

/**
 * Абстрактный класс для классов UserStore и RoleStore.
 *
 * @param <T> - объект.
 */
public abstract class AbstractStore<T extends Base> implements Store<T> {

    private SimpleArray<T> simpleArray;
    private int size;

    /**
     * Конструктор принимает массив объектов и размер массива.
     *
     * @param model - объект.
     * @param size  - размер массива.
     */
    public AbstractStore(T[] model, int size) {
        this.size = size;
        simpleArray = new SimpleArray<>(model, size);
    }

    @Override
    public void add(T model) {
        simpleArray.add(model);
    }

    /**
     * Метод заменяет объект в коллекции по индексу.
     *
     * @param id    - id объекта.
     * @param model - объект.
     * @return - true or false по результату операции.
     */
    @Override
    public boolean replace(String id, T model) {
        boolean result = false;
        for (int i = 0; i < size; i++) {
            if (simpleArray.get(i).getId().equals(id)) {
                simpleArray.set(i, model);
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * Метод удаляет объект из коллекции по индексу.
     * @param id - индекс объекта.
     * @return - true or false по результату операции.
     */
    @Override
    public boolean delete(String id) {
        boolean result = false;
        for (int i = 0; i < size; i++) {
            if (simpleArray.get(i).getId().equals(id)) {
                simpleArray.delete(i);
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * Метод возвращает объект коллекции по id.
     * @param id - индекс объекта.
     * @return - объект с заданным индексом.
     */
    @Override
    public T findById(String id) {
        T result = null;
        for (int i = 0; i < size; i++) {
            if (simpleArray.get(i).getId().equals(id)) {
                result = simpleArray.get(i);
                break;
            }
        }
        return result;
    }
}

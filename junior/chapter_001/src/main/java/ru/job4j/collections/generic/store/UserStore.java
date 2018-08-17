package ru.job4j.collections.generic.store;

public class UserStore extends AbstractStore<User> {

    /**
     * Стандартный конструктор.
     *
     * @param array - массив.
     * @param size  - размер массива.
     */
    public UserStore(User[] array, int size) {
        super(array, size);
    }

}

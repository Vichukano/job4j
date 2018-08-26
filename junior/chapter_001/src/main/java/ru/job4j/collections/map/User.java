package ru.job4j.collections.map;

import java.util.Calendar;

public class User {
    private String name;
    private int children;
    private Calendar birthday;

    public User(String name, int children, Calendar birthday) {
        this.name = name;
        this.children = children;
        this.birthday = birthday;
    }

    /**
     * Переопределяем метод hashCode. Объеты с равными свойствами будут иметь одинаковый хэшкод.
     * @return число типа int.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 0;
        result = prime * result + name.hashCode();
        result = prime * result + children;
        result = prime * result + birthday.hashCode();
        return result;
    }
}

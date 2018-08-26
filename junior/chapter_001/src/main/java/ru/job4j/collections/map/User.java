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
     * Метод equals переопределен.
     *
     * @param o объект для сравнения
     * @return true, если объекты равны, иначе false.
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (this == o) {
            return true;
        }
        User current = (User) o;
        if (name != current.name && children != current.children && birthday != current.birthday) {
            return false;
        } else {
            return true;
        }
    }


    /**
     * Переопределяем метод hashCode. Объеты с равными свойствами будут иметь одинаковый хэшкод.
     *
     * @return число типа int.
     */
     @Override public int hashCode() {
     final int prime = 31;
     int result = 0;
     result = prime * result + name.hashCode();
     result = prime * result + children;
     result = prime * result + birthday.hashCode();
     return result;
     }
}

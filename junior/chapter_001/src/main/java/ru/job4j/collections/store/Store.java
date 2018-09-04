package ru.job4j.collections.store;

import java.util.*;

class Store {
    private HashSet<User> map;

    /**
     * Метод сравнивает две коллекции и возвращает информацию о ее изменении.
     *
     * @param previous первоначальная коллекция.
     * @param current   измененная колекция.
     * @return количество добавленных, удаленных и измененных элементов.
     */
    public String diff(List<User> previous, List<User> current) {
        Collections.sort(current);
        int addedElements = current.get(current.size() - 1).getId() - previous.get(previous.size() - 1).getId();
        int deletedElements = previous.size() - (current.size() - addedElements);
        addListToMap(previous, current);
        int changedElements = map.size() - previous.size() - addedElements;
        return "Added: " + addedElements + " " + "Deleted: " + deletedElements + " " + "Changed: " + changedElements;
    }

    /**
     * Метод помещает элементы коллекции в HashMap. Нужен для вычисления количества изменненных элементов.
     *
     * @param pre первоначальная коллекция.
     * @param cur измененная коллекция.
     */
    private void addListToMap(List<User> pre, List<User> cur) {
        List<User> users = new ArrayList<>();
        users.addAll(pre);
        users.addAll(cur);
        this.map = new HashSet<>();
        for (User user : users) {
            map.add(user);
        }
    }

    static class User implements Comparable<User> {
        int id;
        String name;

        public User(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            User user = (User) o;
            return id == user.id && Objects.equals(name, user.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name);
        }

        @Override
        public int compareTo(User o) {
            return id > o.id ? 1 : id == o.id ? 0 : -1;
        }
    }
}

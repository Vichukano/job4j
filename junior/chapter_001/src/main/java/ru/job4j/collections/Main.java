package ru.job4j.collections;


import ru.job4j.collections.map.User;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Main class.
 */
public class Main {

    public static void main(String[] args) {

        User user1 = new User("One", 2, new GregorianCalendar(1991, 2, 13));
        User user2 = new User("One", 2, new GregorianCalendar(1991, 2, 13));

        /**
         * Метод hashCode переопределен.
         */
        Map<User, Object> map = new HashMap<>();
        map.put(user1, "first");
        map.put(user2, "second");
        System.out.println(map);

        System.out.println(user1.hashCode());
        System.out.println(user2.hashCode());
        System.out.println(user1.equals(user2));
    }
}

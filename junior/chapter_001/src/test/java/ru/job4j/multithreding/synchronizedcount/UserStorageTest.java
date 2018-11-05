package ru.job4j.multithreding.synchronizedcount;

import org.junit.Test;
import ru.job4j.multithreading.synchronizedcount.User;
import ru.job4j.multithreading.synchronizedcount.UserStorage;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class UserStorageTest {
    private User user1 = new User(1, 50);
    private User user2 = new User(2, 100);
    private User user3 = new User(3, 150);

    /**
     * Поток выполнения добавляет объекты User в хранилище.
     */
    private class ThreadUserStorageOne implements Runnable {
        private final UserStorage us;

        private ThreadUserStorageOne(final UserStorage us) {
            this.us = us;
        }

        @Override
        public void run() {
            us.add(user1);
            us.add(user2);
            us.add(user3);
        }
    }

    /**
     * Поток выполнения удаляет объект из коллекции и выполняет метод transfer.
     */
    private class ThreadUserStorageTwo implements Runnable {
        private final UserStorage us;

        private ThreadUserStorageTwo(final UserStorage us) {
            this.us = us;
        }

        @Override
        public void run() {
            us.delete(user2);
            us.transfer(3, 1, 50);
            us.update(user2);
        }
    }

    @Test
    public void whenExecute2ThreadsMustBeSafe() throws InterruptedException {
        UserStorage us = new UserStorage();
        ThreadUserStorageOne one = new ThreadUserStorageOne(us);
        ThreadUserStorageTwo two = new ThreadUserStorageTwo(us);
        Thread t1 = new Thread(one);
        Thread t2 = new Thread(two);
        t1.start();
        t1.join();
        t2.start();
        t2.join();
        assertThat(us.findUserById(1).getAmount(), is(100));
    }
}

package ru.job4j.multithreding.nonblocking;

import org.junit.Test;
import ru.job4j.multithreading.nonblocking.NonBlockingCache;
import ru.job4j.multithreading.nonblocking.OptimisticException;
import ru.job4j.multithreading.nonblocking.User;

import java.util.concurrent.atomic.AtomicReference;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Тест неблокирующего кэша.
 * Первый поток исполнения получает из кэш объект User и изменяет его переменную -имя.
 * После этого поток исполнения переходит в режим wait.
 * Второй поток исполнения получает из кэша тот же объект User, что и первый поток.
 * Дальше применяется копирование объекта User, иначе все манипуляции с переменными
 * объекта в первом потоке будут отражаться во втором потоке и код будез рабоать не верно.
 * После получения копии объекта User, второй поток будит первый поток и ждет его выполнения.
 * Далее скопированному объекту User изменяется поле name.
 * Дальше происходит попытка update, при которой бросается OptimisticException.
 * Исключени бросается так как у User в кэше version = 1, а у объекта, с которым
 * манипулирует второй поток исполнения version = 0;
 */
public class NonBlockingCacheTest {

    @Test
    public void test() throws InterruptedException {
        NonBlockingCache<User> cache = new NonBlockingCache();
        cache.add(new User("First"));
        AtomicReference<Exception> exp = new AtomicReference<>();

        Thread t1 = new Thread(
                () -> {
                    User user1 = cache.get(1);
                    user1.setName("Other");
                    synchronized (this) {
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    cache.update(user1);
                }
        );
        Thread t2 = new Thread(
                () -> {
                    User user = cache.get(1);
                    User user2 = user.copy();
                    try {
                        synchronized (this) {
                            notifyAll();
                        }
                        t1.join();
                        user2.setName("Another one");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        cache.update(user2);
                    } catch (OptimisticException e) {
                        exp.set(e);
                    }
                }
        );
        t1.start();
        Thread.sleep(100);
        t2.start();
        t2.join();
        assertThat(exp.get().getMessage(), is("invalid versions"));
    }
}

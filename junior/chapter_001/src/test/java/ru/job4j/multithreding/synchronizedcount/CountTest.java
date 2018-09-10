package ru.job4j.multithreding.synchronizedcount;

import org.junit.Test;
import ru.job4j.multithreading.synchronizedcount.Count;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


public class CountTest {
    private class ThreadCount implements Runnable {
        private final Count count;

        private ThreadCount(final Count count) {
            this.count = count;
        }


        @Override
        public void run() {
            this.count.increment();
        }
    }

    @Test
    public void whenExecute2ThreadThen2() throws InterruptedException {
        final Count count = new Count();
        ThreadCount threadCount = new ThreadCount(count);
        Thread t1 = new Thread(threadCount);
        Thread t2 = new Thread(threadCount);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        assertThat(count.get(), is(2));
    }
}

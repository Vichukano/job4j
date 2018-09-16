package ru.job4j.multithreding.pool;

import org.junit.Test;
import ru.job4j.multithreading.pool.ThreadPool;

import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPoolTest {

    @Test
    public void whenTreadPoolAddTenWorkItMustDoFourInSameTime() throws InterruptedException {
        ThreadPool pool = new ThreadPool();
        pool.work(new Job());
        pool.work(new Job());
        pool.work(new Job());
        pool.work(new Job());
        pool.work(new Job());
        pool.work(new Job());
        pool.work(new Job());
        pool.work(new Job());
        pool.work(new Job());
        pool.work(new Job());
        Thread.sleep(3000);
        pool.shutdown();

    }

    private static class Job implements Runnable {
        private static AtomicInteger count = new AtomicInteger();
        private Integer id = count.incrementAndGet();

        @Override
        public void run() {
            System.out.println("Job № " + id);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

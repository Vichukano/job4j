package ru.job4j.multithreading.pool;

import ru.job4j.multithreading.wait.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

/**
 * Реализация пула потоков.
 */
public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>();
    private volatile boolean isRunning = true;
    /**
     * Переменная size является количеством процессоров.
     */
    private final int size = Runtime.getRuntime().availableProcessors();

    /**
     * Конструктор создает список из потоков исполнения в количестве size.
     * Каждый поток исполнения запускается.
     */
    public ThreadPool() {
        for (int i = 0; i < size; i++) {
            Thread worker = new Thread(new Worker(tasks));
            threads.add(worker);
            worker.start();
        }
    }

    /**
     * Метод похож на метод execute в интерфейсе Executor.
     * Добавляет в очередь tasks передаваемый объект.
     *
     * @param job объект клаасса, реализующего интерфейс Runnable.
     */
    public void work(Runnable job) {
        if (isRunning) {
            try {
                tasks.offer(job);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Метод закрывает выполнение всех классов Worker.
     */
    public void shutdown() {
        isRunning = false;
    }

    /**
     * Класс выполняет переданную задачу в отдельном потоке.
     * Реализует интерфейс Runnable.
     * В методе run() принимает и запускает task из очереди.
     */
    private final class Worker implements Runnable {
        private final SimpleBlockingQueue<Runnable> tasks;

        public Worker(SimpleBlockingQueue<Runnable> tasks) {
            this.tasks = tasks;
        }

        @Override
        public void run() {
            while (isRunning) {
                Runnable nextTask = null;
                try {
                    nextTask = tasks.poll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    shutdown();
                }
                if (nextTask != null) {
                    nextTask.run();
                }
            }
        }
    }
}

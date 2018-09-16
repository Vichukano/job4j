package ru.job4j.multithreading.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Класс реализация пула потоков для рассылки почты.
 */
public class EmailNotification {
    private final int nThreads = Runtime.getRuntime().availableProcessors();
    private final ExecutorService pool;
    private final String subject = "Notification {username} to email {email}";
    private final String body = "Add a new event to {username}";
    private String newSubject;
    private String newBody;

    /**
     * При вызове конструктора инициализаруется пул потоков в количестве
     * доступных процессоров.
     */
    public EmailNotification() {
        pool = Executors.newFixedThreadPool(nThreads);
    }

    /**
     * Метод заменяется поля в шаблонах.
     *
     * @param user объект модели.
     */
    private void emailTo(User user) {
        newSubject = (subject.replace("{username}", user.getName())).replace("{email}", user.getEmail());
        newBody = body.replace("{username}", user.getName());
    }

    /**
     * Реализован вывод в консоль для наглядности теста.
     *
     * @param subject шаблон.
     * @param body    шаблон.
     * @param email   почта объякта User.
     */
    private void send(String subject, String body, String email) {
        //TODO
        System.out.println(subject);
        System.out.println(body);
        System.out.println(email);
    }

    /**
     * Метод запускает выполнение задачи в потоке.
     *
     * @param user объект модели.
     */
    public void execute(User user) {
        pool.execute(new Worker(user));
    }

    public void close() {
        pool.shutdown();
    }

    /**
     * Метод выплняющий рассылку в отдельном потоке исполнения.
     */
    private class Worker implements Runnable {
        private User user;

        public Worker(User user) {
            this.user = user;
        }

        @Override
        public void run() {
            emailTo(user);
            send(newSubject, newBody, user.getEmail());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

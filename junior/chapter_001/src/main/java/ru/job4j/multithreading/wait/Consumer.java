package ru.job4j.multithreading.wait;

public class Consumer implements Runnable {
    private SimpleBlockingQueue<Integer> q;

    public Consumer(SimpleBlockingQueue<Integer> q) {
        this.q = q;
    }

    @Override
    public void run() {
        int i = 0;
        while (i <= 10) {
            try {
                System.out.println("Consume: " + q.poll());
                i++;
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

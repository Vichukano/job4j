package ru.job4j.multithreading.wait;

public class Producer implements Runnable {
    private SimpleBlockingQueue<Integer> q;

    public Producer(SimpleBlockingQueue<Integer> q) {
        this.q = q;
    }

    @Override
    public void run() {
        int i = 1;
        while (i <= 10) {
            try {
                q.offer(i);
                System.out.println("Produce: " + i);
                i++;
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

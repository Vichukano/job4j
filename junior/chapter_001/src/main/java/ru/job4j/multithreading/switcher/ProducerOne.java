package ru.job4j.multithreading.switcher;

/**
 * Класс описывает добавление в строку значения 1.
 * По заверщению 10 итераций освобождает монитор и прекрашает выполнение метода addValue.
 */
public class ProducerOne implements Runnable {
    private final Switcher switcher;
    private final int value = 1;

    public ProducerOne(Switcher switcher) {
        this.switcher = switcher;
    }

    private void addValue() {
        try {
            switcher.semaphoreTwo.acquire();
            for (int i = 0; i < 10; i++) {
                switcher.addValueToString(value);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        switcher.semaphoreOne.release();
    }

    @Override
    public void run() {
        int i = 0;
        while (i < 5) {
            addValue();
            i++;
        }
    }
}

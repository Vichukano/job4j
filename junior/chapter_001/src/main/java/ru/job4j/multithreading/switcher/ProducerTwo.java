package ru.job4j.multithreading.switcher;

/**
 * Класс описывает добавление в строку значения 2.
 * По заверщению 10 итераций освобождает монитор и прекрашает выполнение метода addValue меняя флаг.
 */
public class ProducerTwo implements Runnable {
    private final Switcher switcher;
    private final int value = 2;

    public ProducerTwo(Switcher switcher) {
        this.switcher = switcher;
    }

    private void addValue() {
        try {
            switcher.semaphoreOne.acquire();
            for (int i = 0; i < 10; i++) {
                switcher.addValueToString(value);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        switcher.semaphoreTwo.release();
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

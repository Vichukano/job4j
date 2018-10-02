package ru.job4j.multithreding;

import org.junit.Test;
import ru.job4j.multithreading.switcher.ProducerOne;
import ru.job4j.multithreading.switcher.ProducerTwo;
import ru.job4j.multithreading.switcher.Switcher;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SwitcherTest {

    @Test
    public void whenSwitchWithFiveIterationShouldReturnCorrectValue() {
        Switcher sw = new Switcher();
        ProducerOne one = new ProducerOne(sw);
        ProducerTwo two = new ProducerTwo(sw);
        Thread t1 = new Thread(one);
        Thread t2 = new Thread(two);
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertThat(sw.getString(), is("1111111111222222222211111111112222222222111111111122222222221111111111222222222211111111112222222222"));
    }
}

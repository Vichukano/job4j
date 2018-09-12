package ru.job4j.multithreding.synchronizedcount;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.collections.list.Container;
import ru.job4j.collections.list.DinamicContainer;
import ru.job4j.multithreading.synchronizedcount.ThreadSafeDinamicContainer;

import java.util.Iterator;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ThreadSafeDinamicContainerTest {
    private ThreadSafeDinamicContainer<Integer> safeContainer;
    private Container<Integer> c;

    @Before
    public void setUp() {
        c = new DinamicContainer<>();
        safeContainer = new ThreadSafeDinamicContainer<>(c);
        safeContainer.add(1);
        safeContainer.add(2);
        safeContainer.add(3);
    }

    @Test
    public void whenGetValueShouldReturnCorrect() {
        assertThat(safeContainer.get(0), is(1));
        assertThat(safeContainer.get(1), is(2));
        assertThat(safeContainer.get(2), is(3));
    }

    @Test
    public void whenDeleteValueThenReduceSize() {
        safeContainer.delete();
        assertThat(safeContainer.getSize(), is(2));
    }

    @Test
    public void whenIterateThenReturnCorrectValue() {
        Iterator<Integer> it = safeContainer.iterator();
        assertThat(it.next(), is(1));
        assertThat(it.next(), is(2));
        assertThat(it.next(), is(3));
    }

    @Test
    public void whenAddValueAfterIterateShouldNotTrowException() {
        Iterator<Integer> it = safeContainer.iterator();
        assertThat(it.next(), is(1));
        safeContainer.add(4);
        assertThat(it.next(), is(2));
        assertThat(it.next(), is(3));
    }


}

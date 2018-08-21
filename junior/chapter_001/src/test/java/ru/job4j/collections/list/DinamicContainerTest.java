package ru.job4j.collections.list;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class DinamicContainerTest {
    private DinamicContainer<Integer> dc;

    @Before
    public void setUp() {
        dc = new DinamicContainer<>(3);
        dc.add(1);
        dc.add(2);
        dc.add(3);
    }

    @Test
    public void whenGetByIndexItMustBeSame() {
        assertThat(dc.get(0), is(1));
        assertThat(dc.get(1), is(2));
        assertThat(dc.get(2), is(3));
    }

    @Test
    public void whenAddValueAtFullArraySizeMustGrowUp() {
        dc.add(4);
        assertThat(dc.getSize(), is(5));
    }

    @Test
    public void whenHasNextAndNextMustReturnIncrementedValue() {
        Iterator<Integer> it = dc.iterator();
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(1));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(2));
    }

    @Test(expected = ConcurrentModificationException.class)
    public void whenArrayModificatedAfterIteratorMustThrowException() {
        Iterator<Integer> it = dc.iterator();
        dc.add(5);
        it.next();
    }
}

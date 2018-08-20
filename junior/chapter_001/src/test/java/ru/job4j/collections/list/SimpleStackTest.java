package ru.job4j.collections.list;

import org.junit.Before;
import org.junit.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SimpleStackTest {
    private SimpleStack<Integer> ss;

    @Before
    public void setUp() {
        ss = new SimpleStack<>(3);
        ss.push(1);
        ss.push(2);
        ss.push(3);
    }

    @Test
    public void whenGetByIndexMustReturnCorrectValue() {
        assertThat(ss.get(0), is(1));
        assertThat(ss.get(1), is(2));
        assertThat(ss.get(2), is(3));
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void whenGetIndexOutOfRangeMustThrowException() {
        ss.get(3);
        ss.get(4);
        ss.get(-3);
    }

    @Test
    public void whenAddValueItMustAppend() {
        ss.push(4);
        assertThat(ss.get(3), is(4));
        ss.push(5);
        assertThat(ss.get(4), is(5));
        ss.push(6);
        assertThat(ss.get(5), is(6));
    }

    @Test
    public void whenDeleteMustReturnDeletedValue() {
        assertThat(ss.poll(), is(3));
        assertThat(ss.poll(), is(2));
        assertThat(ss.poll(), is(1));
    }

    @Test
    public void whenNextMustReturnCorrectValue() {
        Iterator<Integer> it = ss.iterator();
        assertThat(it.next(), is(1));
        assertThat(it.next(), is(2));
        assertThat(it.next(), is(3));
    }

    @Test
    public void whenContainerHaveNoNextHasNextMustBeFalse() {
        Iterator<Integer> it = ss.iterator();
        it.next();
        it.next();
        it.next();
        assertThat(it.hasNext(), is(false));
    }

    @Test(expected = ConcurrentModificationException.class)
    public void whenContainerChangedAfterIteratorCallMustThrowException() {
        Iterator<Integer> it = ss.iterator();
        ss.push(4);
        it.next();
    }
}

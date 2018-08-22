package ru.job4j.collections.list;

import org.junit.Before;
import org.junit.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SimpleQueueTest {
    private SimpleQueue<Integer> sq;

    @Before
    public void setUp() {
        sq = new SimpleQueue<>();
        sq.push(1);
        sq.push(2);
        sq.push(3);
    }

    @Test
    public void whenPollMustReturnCorrectValue() {
        assertThat(sq.poll(), is(1));
        assertThat(sq.poll(), is(2));
        assertThat(sq.poll(), is(3));
    }

    @Test
    public void whenAddValueMustAppend() {
        sq.push(4);
        assertThat(sq.get(3), is(4));
        sq.push(5);
        assertThat(sq.get(4), is(5));
        sq.push(6);
        assertThat(sq.get(5), is(6));
        sq.push(7);
        assertThat(sq.get(6), is(7));
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void whenGetIndexOutOfRangeMustThrowException() {
        sq.get(3);
        sq.get(4);
        sq.get(-3);
    }

    @Test
    public void whenNextMustReturnCorrectValue() {
        Iterator<Integer> it = sq.iterator();
        assertThat(it.next(), is(1));
        assertThat(it.next(), is(2));
        assertThat(it.next(), is(3));
    }

    @Test
    public void whenContainerHaveNoNextHasNextMustBeFalse() {
        Iterator<Integer> it = sq.iterator();
        it.next();
        it.next();
        it.next();
        assertThat(it.hasNext(), is(false));
    }

    @Test(expected = ConcurrentModificationException.class)
    public void whenContainerChangedAfterIteratorCallMustThrowException() {
        Iterator<Integer> it = sq.iterator();
        sq.push(4);
        it.next();
    }

    @Test
    public void whenIterateNullValuesThenSkipIt() {
        sq.push(null);
        sq.push(null);
        sq.push(4);
        sq.push(5);
        Iterator<Integer> it = sq.iterator();
        assertThat(it.next(), is(1));
        assertThat(it.next(), is(2));
        assertThat(it.next(), is(3));
        assertThat(it.next(), is(4));
        assertThat(it.next(), is(5));
    }
}


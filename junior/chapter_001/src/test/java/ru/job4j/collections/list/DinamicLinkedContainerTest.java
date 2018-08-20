package ru.job4j.collections.list;

import org.junit.Before;
import org.junit.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class DinamicLinkedContainerTest {
    private DinamicLinkedContainer<Integer> dlc;
    private Iterator<Integer> it;

    @Before
    public void setUp() {
        dlc = new DinamicLinkedContainer<>();
        dlc.add(1);
        dlc.add(2);
        dlc.add(3);
    }

    @Test
    public void whenAddValuesMustChangeSize() {
        assertThat(dlc.getSize(), is(3));
        dlc.add(4);
        assertThat(dlc.getSize(), is(4));
        dlc.add(5);
        assertThat(dlc.getSize(), is(5));
        dlc.add(6);
        assertThat(dlc.getSize(), is(6));
    }

    @Test
    public void whenGetByIndexItMustBeSame() {
        assertThat(dlc.get(0), is(1));
        assertThat(dlc.get(1), is(2));
        assertThat(dlc.get(2), is(3));
    }

    @Test
    public void whenIterateMustBeCorrectValue() {
        it = dlc.iterator();
        assertThat(it.next(), is(1));
        assertThat(it.next(), is(2));
        assertThat(it.next(), is(3));
    }

    @Test(expected = ConcurrentModificationException.class)
    public void whenChangeContainerAfterIterateMustTrowException() {
        it = dlc.iterator();
        assertThat(it.next(), is(1));
        assertThat(it.next(), is(2));
        dlc.add(4);
        while (it.hasNext()){
            it.next();
        }
    }

    @Test
    public void whenIterateNullMustReturnNextValue() {
        dlc.add(null);
        dlc.add(5);
        dlc.add(null);
        dlc.add(null);
        dlc.add(6);
        it = dlc.iterator();
        it.hasNext();
        assertThat(it.next(), is(1));
        it.hasNext();
        assertThat(it.next(), is(2));
        it.hasNext();
        assertThat(it.next(), is(3));
        it.hasNext();
        assertThat(it.next(), is(5));
        it.hasNext();
        assertThat(it.next(), is(6));
    }
}

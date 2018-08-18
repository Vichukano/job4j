package ru.job4j.collections.generic.simplearray;


import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SimpleArrayTest {
    private Integer[] arr1;
    private String[] arr2;
    private Double[] arr3;
    private Iterator<Integer> it1;
    private Iterator<String> it2;
    private Iterator<Double> it3;
    private SimpleArray<Integer> sa1;
    private SimpleArray<String> sa2;
    private SimpleArray<Double> sa3;

    @Before
    public void setUp() {
        sa1 = new SimpleArray<>(3);
        sa2 = new SimpleArray<>(3);
        sa3 = new SimpleArray<>(3);
        sa1.add(1);
        sa1.add(2);
        sa1.add(3);
        sa2.add("1");
        sa2.add("2");
        sa2.add("3");
        sa3.add(1.1);
        sa3.add(2.2);
        sa3.add(3.3);
        it1 = sa1.iterator();
        it2 = sa2.iterator();
        it3 = sa3.iterator();
    }

    @Test
    public void whenGetValueItReturnSame() {
        assertThat(it1.next(), is(1));
        assertThat(it1.next(), is(2));
        assertThat(it1.next(), is(3));
        assertThat(it2.next(), is("1"));
        assertThat(it2.next(), is("2"));
        assertThat(it2.next(), is("3"));
        assertThat(it3.next(), is(1.1));
        assertThat(it3.next(), is(2.2));
        assertThat(it3.next(), is(3.3));
    }

    @Test
    public void dataTypesMustMuch() {
        assertThat(sa1.get(0).getClass().getName(), is("java.lang.Integer"));
        assertThat(sa2.get(0).getClass().getName(), is("java.lang.String"));
        assertThat(sa3.get(0).getClass().getName(), is("java.lang.Double"));
    }

    @Test
    public void hasNextInvocationDoesNotAffectRetrievalOrder() {
        assertThat(it1.hasNext(), is(true));
        it1.next();
        assertThat(it1.hasNext(), is(true));
        it1.next();
        assertThat(it1.hasNext(), is(true));
        it1.next();
        assertThat(it1.hasNext(), is(false));
        it2.next();
        assertThat(it2.hasNext(), is(true));
        it2.next();
        assertThat(it2.hasNext(), is(true));
        it2.next();
        assertThat(it2.hasNext(), is(false));
        it3.next();
        assertThat(it3.hasNext(), is(true));
        it3.next();
        assertThat(it3.hasNext(), is(true));
        it3.next();
        assertThat(it3.hasNext(), is(false));
    }

    @Test
    public void whenDeleteValueMustDecrementArrayLength() {
        assertThat(sa1.get(0), is(1));
        sa1.delete(0);
        assertThat(sa1.get(0), is(2));
        assertThat(sa1.get(1), is(3));
    }

    @Test
    public void whenSetValueItMustChanged() {
        assertThat(sa1.get(0), is(1));
        sa1.set(0, 5);
        assertThat(sa1.get(0), is(5));
        assertThat(sa2.get(0), is("1"));
        sa2.set(0, "5");
        assertThat(sa2.get(0), is("5"));
        assertThat(sa3.get(0), is(1.1));
        sa3.set(0, 5.5);
        assertThat(sa3.get(0), is(5.5));
    }

    @Test(expected = NoSuchElementException.class)
    public void whenArrayLengthReachedTrowsException() {
        assertThat(it1.next(), is(1));
        assertThat(it1.next(), is(2));
        assertThat(it1.next(), is(3));
        it1.next();
    }
}

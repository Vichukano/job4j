package ru.job4j.collections.set;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SimpleSetTest {
    private SimpleSet<Integer> ss;
    private SimpleSet<String> strings;

    @Before
    public void setUp() {
        ss = new SimpleSet<>();
        ss.add(1);
        ss.add(2);
        ss.add(3);
        strings = new SimpleSet<>();
        strings.add("aaa");
        strings.add("bbb");
        strings.add("ccc");
    }

    @Test
    public void whenIterateSimpleSetThenReturnNextValue() {
        Iterator<Integer> it = ss.iterator();
        assertThat(it.next(), is(1));
        assertThat(it.next(), is(2));
        assertThat(it.next(), is(3));
    }

    @Test
    public void whenAddDuplicateValueThenNotAddIt() {
        ss.add(2);
        ss.add(4);
        Iterator<Integer> it = ss.iterator();
        assertThat(it.next(), is(1));
        assertThat(it.next(), is(2));
        assertThat(it.next(), is(3));
        assertThat(it.next(), is(4));
    }

    @Test
    public void whenIterateSimpleSetThenReturnNextValueForString() {
        Iterator<String> it = strings.iterator();
        assertThat(it.next(), is("aaa"));
        assertThat(it.next(), is("bbb"));
        assertThat(it.next(), is("ccc"));
    }

    @Test
    public void whenAddDuplicateValueThenNotAddItForString() {
        strings.add("aaa");
        strings.add("ddd");
        Iterator<String> it = strings.iterator();
        assertThat(it.next(), is("aaa"));
        assertThat(it.next(), is("bbb"));
        assertThat(it.next(), is("ccc"));
        assertThat(it.next(), is("ddd"));


    }
}

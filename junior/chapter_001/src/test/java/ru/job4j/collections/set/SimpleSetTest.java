package ru.job4j.collections.set;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SimpleSetTest {
    private SimpleSet<Integer> ss;
    private SimpleSet<String> strings;
    private SimpleSet<Object> objects;

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

    @Test
    public void whenAddDuplicateObjectThenNotAddIt() {
        Object o1 = new Object();
        Object o2 = new Object();
        Object o3 = new Object();
        Object o4 = new Object();
        objects = new SimpleSet<>();
        objects.add(o1);
        objects.add(o2);
        objects.add(o3);
        objects.add(o2);
        objects.add(o4);
        Iterator<Object> it = objects.iterator();
        assertThat(it.next(), is(o1));
        assertThat(it.next(), is(o2));
        assertThat(it.next(), is(o3));
        assertThat(it.next(), is(o4));
    }
}

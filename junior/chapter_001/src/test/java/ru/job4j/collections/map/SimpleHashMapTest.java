package ru.job4j.collections.map;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.collections.map.SimpleHashMap;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SimpleHashMapTest {
    private SimpleHashMap<Integer, String> simpleHashMap;


    @Before
    public void setUp() {
        simpleHashMap = new SimpleHashMap<>();
        simpleHashMap.insert(1, "one");
        simpleHashMap.insert(2, "two");
        simpleHashMap.insert(3, "three");
    }

    @Test
    public void whenAddValueToMapThenReturnTrue() {
        assertThat(simpleHashMap.insert(4, "four"), is(true));
        assertThat(simpleHashMap.insert(5, "five"), is(true));
        assertThat(simpleHashMap.insert(6, "six"), is(true));
        assertThat(simpleHashMap.insert(7, "seven"), is(true));
    }

    @Test
    public void whenGetShouldReturnCorrectValue() {
        assertThat(simpleHashMap.get(1), is("one"));
        assertThat(simpleHashMap.get(2), is("two"));
        assertThat(simpleHashMap.get(3), is("three"));
    }

    @Test(expected = NoSuchElementException.class)
    public void whenGetIncorrectKeyShouldThrowException() {
        simpleHashMap.get(4);
        simpleHashMap.get(5);
        simpleHashMap.get(6);
    }

    @Test
    public void whenGetByValueShouldReturnCorrectKey() {
        assertThat(simpleHashMap.getByKey("one"), is(1));
        assertThat(simpleHashMap.getByKey("two"), is(2));
        assertThat(simpleHashMap.getByKey("three"), is(3));
    }

    @Test(expected = NoSuchElementException.class)
    public void whenGetByValueIncorrectShouldTrowException() {
        simpleHashMap.getByKey("five");
        simpleHashMap.getByKey("six");
        simpleHashMap.getByKey("seven");
    }

    @Test
    public void whenDeleteValueShouldReturnTrue() {
        assertThat(simpleHashMap.delete(1), is(true));
        assertThat(simpleHashMap.delete(2), is(true));
        assertThat(simpleHashMap.delete(3), is(true));
    }

    @Test
    public void whenDeleteIncorrectValueShouldReturnFalse() {
        assertThat(simpleHashMap.delete(4), is(false));
        assertThat(simpleHashMap.delete(5), is(false));
        assertThat(simpleHashMap.delete(6), is(false));
    }

    @Test
    public void whenAddKeyWithSameHashShouldReturnFalse() {
        assertThat(simpleHashMap.insert(1, "one"), is(false));
        assertThat(simpleHashMap.insert(2, "one"), is(false));
        assertThat(simpleHashMap.insert(3, "one"), is(false));
    }

    @Test
    public void whenIterateShouldReturnCorrectValue() {
        Iterator<String> it = simpleHashMap.iterator();
        assertThat(it.next(), is("one"));
        assertThat(it.next(), is("two"));
        assertThat(it.next(), is("three"));
    }

    @Test
    public void whenHasNextShouldReturnCorrectValue() {
        Iterator<String> it = simpleHashMap.iterator();
        String result = "";
        while (it.hasNext()) {
            result = result + " " + it.next();
        }
        assertThat(result, is(" one two three"));
    }

    @Test(expected = ConcurrentModificationException.class)
    public void whenModificatedAfterIteratorCallShouldThrowException() {
        Iterator<String> it = simpleHashMap.iterator();
        simpleHashMap.insert(9, "nine");
        it.next();
    }
}

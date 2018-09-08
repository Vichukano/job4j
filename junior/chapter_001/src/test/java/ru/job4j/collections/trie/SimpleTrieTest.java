package ru.job4j.collections.trie;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SimpleTrieTest {
    private SimpleTrie st;

    @Before
    public void setUp() {
        st = new SimpleTrie();
        st.put("мама", 1);
        st.put("мыла", 2);
        st.put("раму", 3);
    }

    @Test
    public void whenGetByKeyShouldReturnCorrectValue() {
        assertThat(st.getByKey("мама").toArray(), is(new int[] {1}));
        assertThat(st.getByKey("мыла").toArray(), is(new int[] {2}));
        assertThat(st.getByKey("раму").toArray(), is(new int[] {3}));
    }

    @Test
    public void whenKeyContainsInTrieShouldReturnTrue() {
        assertThat(st.containsKey("мама"), is(true));
        assertThat(st.containsKey("мыла"), is(true));
        assertThat(st.containsKey("раму"), is(true));
    }

    @Test
    public void whenKeyNotContainsInTrieShouldReturnFalse() {
        assertThat(st.containsKey("маманя"), is(false));
        assertThat(st.containsKey("помыла"), is(false));
        assertThat(st.containsKey("рамураму"), is(false));
        assertThat(st.containsKey("папа"), is(false));
    }

    @Test
    public void whenPutNewWordItShouldBeCorrect() {
        st.put("мамаша", 4);
        assertThat(st.containsKey("мамаша"), is(true));
        assertThat(st.getByKey("мамаша").toArray(), is(new int[] {4}));
    }
}

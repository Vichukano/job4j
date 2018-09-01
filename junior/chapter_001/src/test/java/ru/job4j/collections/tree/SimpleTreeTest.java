package ru.job4j.collections.tree;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SimpleTreeTest {
    private SimpleTreeImpl<Integer> simpleTree;

    @Before
    public void setUp() {
        simpleTree = new SimpleTreeImpl<>(1);
        simpleTree.add(1, 2);
        simpleTree.add(1, 3);
        simpleTree.add(2, 4);
        simpleTree.add(3, 5);
        simpleTree.add(3, 6);
        simpleTree.add(3, 7);
    }

    /**
     * Проверка на добавление элементов в дерево.
     */
    @Test
    public void whenAddValueShouldReturnTrue() {
        assertThat(simpleTree.add(7, 2), is(true));
        assertThat(simpleTree.add(7, 3), is(true));
        assertThat(simpleTree.add(7, 5), is(true));
    }

    /**
     * Проверка на добавление элементов в дерево.
     */
    @Test
    public void whenAddValueToIncorrectParentShouldReturnFalse() {
        assertThat(simpleTree.add(9, 1), is(false));
        assertThat(simpleTree.add(11, 3), is(false));
        assertThat(simpleTree.add(15, 5), is(false));
    }

    /**
     * Проверка на добалвение дубликатов.
     */
    @Test
    public void whenAddDuplicatedValueShouldReturnFalse() {
        assertThat(simpleTree.add(1, 2), is(false));
        assertThat(simpleTree.add(2, 4), is(false));
        assertThat(simpleTree.add(3, 5), is(false));
        assertThat(simpleTree.add(3, 6), is(false));
    }

    /**
     * Проверка метода findBy.
     */
    @Test
    public void whenFindByContainsValueShouldReturnTrue() {
        assertThat(simpleTree.findBy(5).isPresent(), is(true));
        assertThat(simpleTree.findBy(4).isPresent(), is(true));
        assertThat(simpleTree.findBy(3).isPresent(), is(true));
        assertThat(simpleTree.findBy(2).isPresent(), is(true));
        assertThat(simpleTree.findBy(6).isPresent(), is(true));
        assertThat(simpleTree.findBy(7).isPresent(), is(true));
    }

    /**
     * Проверка метода findBy.
     */
    @Test
    public void whenFindByIncorrectValueShouldReturnFalse() {
        assertThat(simpleTree.findBy(8).isPresent(), is(false));
        assertThat(simpleTree.findBy(0).isPresent(), is(false));
        assertThat(simpleTree.findBy(9).isPresent(), is(false));
        assertThat(simpleTree.findBy(20).isPresent(), is(false));
        assertThat(simpleTree.findBy(10).isPresent(), is(false));
    }

    /**
     * Проверка итератора.
     */
    @Test
    public void whenIterateNextShouldReturnCorrectValue() {
        Iterator<Integer> it = simpleTree.iterator();
        assertThat(it.next(), is(1));
        assertThat(it.next(), is(2));
        assertThat(it.next(), is(3));
        assertThat(it.next(), is(4));
        assertThat(it.next(), is(5));
        assertThat(it.next(), is(6));
        assertThat(it.next(), is(7));
    }
}

package ru.job4j.collections.list;


import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
public class NodeTest {
    private Node<Integer> first;
    private Node<Integer> two;
    private Node<Integer> third;
    private Node<Integer> four;
    @Before
    public void setUp() {
        first = new Node<>(1);
        two = new Node<>(2);
        third = new Node<>(3);
        four = new Node<>(4);
    }

    @Test
    public void whenListHasCycleThenReturnTrue() {
        first.next = two;
        two.next = third;
        third.next = four;
        four.next = first;
        Node node = new Node();
        assertThat(node.hasCycle(first), is(true));
    }

    @Test
    public void whenListHasCycleInMidleThenReturnTrue() {
        first.next = two;
        two.next = third;
        third.next = two;
        four.next = first;
        Node node = new Node();
        assertThat(node.hasCycle(first), is(true));
    }

    @Test
    public void whenListHaveNoCycleThenReturnFalse() {
        first.next = two;
        two.next = third;
        third.next = four;
        four.next = null;
        Node node = new Node();
        assertThat(node.hasCycle(first), is(false));
    }
}

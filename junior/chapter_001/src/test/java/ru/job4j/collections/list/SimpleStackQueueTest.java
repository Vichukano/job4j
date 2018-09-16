package ru.job4j.collections.list;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


public class SimpleStackQueueTest {
    private SimpleStackQueue<Integer> simpleStackQueue;

    @Before
    public void setup() {
        simpleStackQueue = new SimpleStackQueue<>();
    }

    @Test
    public void whenPushValueThenChangeSize() {
        assertThat(simpleStackQueue.size(), is(0));
        simpleStackQueue.push(1);
        assertThat(simpleStackQueue.size(), is(1));
        simpleStackQueue.push(2);
        assertThat(simpleStackQueue.size(), is(2));
        simpleStackQueue.push(3);
        assertThat(simpleStackQueue.size(), is(3));
    }

    @Test
    public void whenPollThenReturnAndDeleteFirstValue() {
        simpleStackQueue.push(1);
        simpleStackQueue.push(2);
        simpleStackQueue.push(3);
        assertThat(simpleStackQueue.size(), is(3));
        assertThat(simpleStackQueue.poll(), is(1));
        assertThat(simpleStackQueue.size(), is(2));
    }
}

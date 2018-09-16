package ru.job4j.collections.list;


import com.sun.org.apache.xalan.internal.xsltc.compiler.util.StringStack;

import java.util.Stack;

public class SimpleStackQueue<E> {
    private SimpleStack<E> stack;

    public SimpleStackQueue() {
        stack = new SimpleStack<>();
    }

    public void push(E value) {
        stack.push(value);
    }


    public E poll() {
        SimpleStack<E> temp = new SimpleStack<>();
        int size = this.size();
        for (int i = 0; i < size; i++) {
            temp.push(stack.poll());
        }
        stack = temp;
        return stack.poll();
    }


    public int size() {
        return stack.size();
    }
}

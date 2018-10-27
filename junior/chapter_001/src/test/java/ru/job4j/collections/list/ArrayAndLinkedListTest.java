package ru.job4j.collections.list;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;


public class ArrayAndLinkedListTest {
    private LinkedList<Integer> linked;
    private ArrayList<Integer> array;

    @Before
    public void setUp() {
        linked = new LinkedList<>();
        array = new ArrayList<>();
    }

    @Test
    public void addElementToFirst() {
        System.out.println("Add elements to first.");
        long start = System.currentTimeMillis();
        for (int i = 0; i < 50_000; i++) {
            linked.add(0, 1);
        }
        long end = System.currentTimeMillis();
        System.out.println("Linked: " + (end - start));
        start = System.currentTimeMillis();
        for (int i = 0; i < 50_000; i++) {
            array.add(0, 1);
        }
        end = System.currentTimeMillis();
        System.out.println("Array: " + (end - start));
    }

    @Test
    public void addElementToLast() {
        System.out.println("Add elements to last.");
        long start = System.currentTimeMillis();
        for (int i = 0; i < 50_000; i++) {
            linked.addLast(1);
        }
        long end = System.currentTimeMillis();
        System.out.println("Linked: " + (end - start));
        start = System.currentTimeMillis();
        for (int i = 0; i < 50_000; i++) {
            array.add(1);
        }
        end = System.currentTimeMillis();
        System.out.println("Array: " + (end - start));
    }

    @Test
    public void addElementToCentre() {
        System.out.println("Add elements to centre.");
        for (int i = 0; i < 10_000; i++) {
            linked.add(1);
            array.add(1);
        }
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10_000; i++) {
            linked.add(5_000, 1);
        }
        long end = System.currentTimeMillis();
        System.out.println("Linked: " + (end - start));
        start = System.currentTimeMillis();
        for (int i = 0; i < 10_000; i++) {
            array.add(5_000, 1);
        }
        end = System.currentTimeMillis();
        System.out.println("Array: " + (end - start));
    }


}

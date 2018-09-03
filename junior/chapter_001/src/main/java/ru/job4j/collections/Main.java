package ru.job4j.collections;


import java.util.ArrayList;
import java.util.List;

/**
 * Main class.
 */
public class Main {


    public static void main(String[] args) {
        int[] a = {1, 2, 3};
        int[] b = {1, 2, 3};
        System.out.println(a.hashCode());
        System.out.println(b.hashCode());
        List<Integer> arr = new ArrayList<>();
        for (int i : a) {
            arr.add(i);
        }
        List<Integer> arr2 = new ArrayList<>();
        for (int i : b) {
            arr2.add(i);
        }

        System.out.println(arr.hashCode());
        System.out.println(arr2.hashCode());

    }
}

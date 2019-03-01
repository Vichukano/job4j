package ru.job4j.barbell;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlatesCalculatorTest {

    @Test
    public void platesCalculatorTest() {
        List<Integer> list1 = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6));
        List<Integer> list2 = new ArrayList<>(Arrays.asList(1, 2, 3, 6));
        List<Integer> list3 = new ArrayList<>(Arrays.asList(4, 3, 3, 3, 3));
        List<Integer> list4 = new ArrayList<>(Arrays.asList(3, 4, 3, 3, 2));
        List<Integer> list5 = new ArrayList<>(Arrays.asList(20, 18, 2, 1, 5));
        List<Integer> list6 = new ArrayList<>(Arrays.asList(9, 7, 5, 3, 1, 11));
        List<Integer> list7 = new ArrayList<>(Arrays.asList(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2));
        List<Integer> list8 = new ArrayList<>(Arrays.asList(19, 17, 15, 13, 11, 9, 7, 5, 3, 1));
        List<Integer> list9 = new ArrayList<>(Arrays.asList(19, 17, 15, 13, 11, 9, 7, 5, 3));
        List<Integer> list10 = new ArrayList<>(Arrays.asList(1, 2, 5));
        PlatesCalculator calculator = new PlatesCalculator();
    }


}

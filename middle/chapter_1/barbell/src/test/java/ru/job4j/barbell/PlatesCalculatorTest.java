package ru.job4j.barbell;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PlatesCalculatorTest {

    @Test
    public void platesCalculatorTest() {
        int[] arr = {2, 3, 7, 8, 3, 4, 5, 6, 9, 9, 7, 11, 3};
        int[] arr1 = {1, 2, 3, 6, 8, 9, 12};
        int[] arr2 = {1, 2, 3, 4, 5, 6};
        int[] arr3 = {8, 3, 3, 4, 13};
        int[] arr4 = {1, 2, 3, 6};
        int[] arr5 = {1, 2};
        assertThat(new PlatesCalculator().run(arr), is(74));
        assertThat(new PlatesCalculator().run(arr1), is(40));
        assertThat(new PlatesCalculator().run(arr2), is(20));
        assertThat(new PlatesCalculator().run(arr3), is(0));
        assertThat(new PlatesCalculator().run(arr4), is(12));
        assertThat(new PlatesCalculator().run(arr5), is(0));
    }


}

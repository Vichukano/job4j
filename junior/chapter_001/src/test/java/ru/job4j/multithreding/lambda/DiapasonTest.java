package ru.job4j.multithreding.lambda;

import org.junit.Test;
import ru.job4j.lambda.Diapason;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class DiapasonTest {

    @Test
    public void linearFunctionTest() {
        Diapason d = new Diapason();
        List<Double> result = d.diapason(1, 5,
                x -> x
        );
        List<Double> test = Arrays.asList(1D, 2D, 3D, 4D);
        assertThat(result, is(test));
    }

    @Test
    public void quadraticFunctionTest() {
        Diapason d = new Diapason();
        List<Double> result = d.diapason(1, 5,
                x -> x * x
        );
        List<Double> test = Arrays.asList(1D, 4D, 9D, 16D);
        assertThat(result, is(test));
    }

    @Test
    public void logFunctionTest() {
        Diapason d = new Diapason();
        List<Double> result = d.diapason(1, 5,
                x -> Math.log(x)
        );
        List<Double> test = Arrays.asList(Math.log(1), Math.log(2), Math.log(3), Math.log(4));
        assertThat(result, is(test));
    }
}

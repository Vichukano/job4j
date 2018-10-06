package ru.job4j.lambda;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Diapason {

    public List<Double> diapason(int start, int end, Function<Double, Double> func) {
        List<Double> buffer = new ArrayList<>();
        for (int i = start; i != end; i++) {
            buffer.add(
              func.apply((double) i)
            );
        }
        return buffer;
    }
}
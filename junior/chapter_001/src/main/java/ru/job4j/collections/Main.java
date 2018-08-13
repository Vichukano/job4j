package ru.job4j.collections;

/**
 * Main class.
 */
public class Main {
    public static void main(String[] args) {
        int[][] arr = {
                {1, 2, 5, 7},
                {5, 6, 3},
                {1, 2}
        };
        MatrixIterator matrixIterator = new MatrixIterator(arr);

        while (matrixIterator.hasNext()) {
            System.out.println(matrixIterator.next());
        }
    }
}

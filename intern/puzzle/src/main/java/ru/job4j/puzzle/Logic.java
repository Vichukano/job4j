package ru.job4j.puzzle;

import ru.job4j.puzzle.figures.*;


/**
 * //TODO add comments.
 *
 * @author Petr Arsentev (parsentev@yandex.ru)
 * @version $Id$
 * @since 0.1
 */
public class Logic {
    private final int size;
    private final Figure[] figures;
    private int index = 0;

    public Logic(int size) {
        this.size = size;
        this.figures = new Figure[size * size];
    }

    public void add(Figure figure) {
        this.figures[this.index++] = figure;
    }

    public boolean move(Cell source, Cell dest) {
        boolean rst = false;
        int index = this.findBy(source);
        if (index != -1) {
            Cell[] steps = this.figures[index].way(source, dest);
            if (this.isFree(steps)) {
                rst = true;
                this.figures[index] = this.figures[index].copy(dest);
            }
        }
        return rst;
    }

    public boolean isFree(Cell... cells) {
        boolean result = cells.length > 0;
        for (Cell cell : cells) {
            if (this.findBy(cell) != -1) {
                result = false;
                break;
            }
        }
        return result;
    }

    public void clean() {
        for (int position = 0; position != this.figures.length; position++) {
            this.figures[position] = null;
        }
        this.index = 0;
    }

    private int findBy(Cell cell) {
        int rst = -1;
        for (int index = 0; index != this.figures.length; index++) {
            if (this.figures[index] != null && this.figures[index].position().equals(cell)) {
                rst = index;
                break;
            }
        }
        return rst;
    }

    public boolean isWin() {
        int[][] table = this.convert();
        boolean result = false;
        if (this.isVerticalWin(table) || this.isHorizontalWin(table)) {
            result = true;
        }
        return result;
    }

    /**
     * Метод проверяет состоит ли хотя бы одна колонка матрицы из 1.
     *
     * @param table матрицы, которую необходимо проверить.
     * @return true, если хотя бы одна колонка матрицы состоит из 1, иначе false.
     */
    private boolean isVerticalWin(int[][] table) {
        boolean result = false;
        int sum = 0;
        for (int i = 0; i != table[0].length; i++) {
            sum = 0;
            for (int j = 0; j != table.length; j++) {
                sum = sum + table[j][i];
                if (sum == 5) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Метод проверяет, состоит ли хотя бы одна строка матрицы из 1.
     *
     * @param table матрица, которую необходимо проверить.
     * @return true, если хотя бы одна строка матрицы состоит из 1, иначе false.
     */
    private boolean isHorizontalWin(int[][] table) {
        boolean result = false;
        int sum = 0;
        for (int i = 0; i != table.length; i++) {
            sum = 0;
            for (int j = 0; j != table[i].length; j++) {
                sum = sum + table[i][j];
                if (sum == 5) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    public int[][] convert() {
        int[][] table = new int[this.size][this.size];
        for (int row = 0; row != table.length; row++) {
            for (int cell = 0; cell != table.length; cell++) {
                int position = this.findBy(new Cell(row, cell));
                if (position != -1 && this.figures[position].movable()) {
                    table[row][cell] = 1;
                }
            }
        }
        return table;
    }
}

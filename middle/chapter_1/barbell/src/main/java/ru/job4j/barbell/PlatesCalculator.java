package ru.job4j.barbell;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Класс описывает реализацию жадного алгоритма для поиска
 * оптимальной суммы диском из набора для уравновешивания штанги.
 * <b>ПРЕДУПРЕЖДЕНИЕ!!!</b>
 * В реальной жизни так делать НЕ нужно!
 * Диски на штангу надо навешивать симметричного размера и веса,
 * иначе центр тяжести штанги смещается, что может привести к травме.
 */
public class PlatesCalculator {
    private final Logger logger = LogManager.getLogger(PlatesCalculator.class);
    private List<Integer> left = new ArrayList<>();
    private List<Integer> tmp = new ArrayList<>();
    private List<Integer> right = new ArrayList<>();

    public int run(int[] plates) {
        Arrays.sort(plates);
        for (int i = plates.length - 1; i >= 0; i--) {
            logger.debug("In plates for statement.");
            if (this.sum(left) + plates[i] < (this.sum(plates) - (this.sum(left) + plates[i]))) {
                left.add(plates[i]);
            } else if (this.sum(left) + plates[i] == (this.sum(plates) - (this.sum(left) + plates[i]))) {
                left.add(plates[i]);
                for (int j = 0; j < i; j++) {
                    tmp.add(plates[j]);
                }
                break;
            } else {
                tmp.add(plates[i]);
            }
        }
        for (int i = 0; i < tmp.size(); i++) {
            logger.debug("In tmp for statement.");
            if (this.sum(right) + tmp.get(i) < (this.sum(left))) {
                right.add(tmp.get(i));
                logger.debug(right);
            } else if (this.sum(right) + tmp.get(i) == (this.sum(left))) {
                right.add(tmp.get(i));
                logger.debug(right);
                break;
            }
        }
        if (this.sum(left) != this.sum(right)) {
            System.out.println("Cant find optimal solution.");
            return 0;
        }
        System.out.println(left);
        System.out.println(right);
        System.out.println(this.sum(left) + this.sum(right));
        return this.sum(left) + this.sum(right);
    }

    private int sum(List<Integer> arr) {
        return arr.stream().reduce(0, Integer::sum);
    }

    private int sum(int[] arr) {
        return Arrays.stream(arr).reduce(0, Integer::sum);
    }

    public static void main(String[] args) {
        int[] arr = {2, 3, 7, 8, 3, 4, 5, 6, 9, 9, 7, 11, 3};
        new PlatesCalculator().run(arr);
    }
}

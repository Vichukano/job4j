package ru.job4j.barbell;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Класс описывает реализацию жадного алгоритма для поиска
 * оптимальной суммы диском из набора для уравновешивания штанги.
 * <b>ПРЕДУПРЕЖДЕНИЕ!!!</b>
 * В реальной жизни так делать НЕ нужно!
 * Диски на штангу надо навешивать симметричного размера и веса,
 * иначе центр тяжести штанги смещается, что может привести к травме.
 */

//алгоритм работает лучше, но не считает максимальную сумму, а рандомную.
// Доделать расчет максимальный суммы через жадный алгоритм - СДЕЛАНО.
// Написать корректный вывод результата суммы.
public class PlatesCalculator {
    private final Logger logger = LogManager.getLogger(PlatesCalculator.class);
    private int count;

    public int run(List<Integer> tmp) {
        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();
        tmp.sort(Comparator.naturalOrder());
        logger.debug("Sorted list: {}", tmp);
        List<Integer> plates = new ArrayList<>(tmp);
        if (this.sum(plates) % 2 != 0) {
            for (int i = 0; i < plates.size(); i++) {
                if ((this.sum(plates) - plates.get(i)) % 2 == 0) {
                    logger.debug("Removed element: {}", plates.get(i));
                    plates.remove(i);
                    break;
                }
            }
        }
        //Жадный алгоритм. Берем половину суммы и сравниваем элементы с конца.
        //Если достигли половины суммы, то завершаем.
        int result = 0;
        int halfSum = this.sum(plates) / 2;
        logger.debug("Half of sum: {}", halfSum);
        for (int i = plates.size() - 1; i >= 0; i--) {
            if (halfSum >= (this.sum(left) + plates.get(i))) {
                left.add(plates.get(i));
                logger.debug("Added to left: {}", plates.get(i));
            } else {
                right.add(plates.get(i));
                logger.debug("Added to right: {}", plates.get(i));
            }
            if (this.sum(left) == this.sum(right)) {
                result = (this.sum(left) + this.sum(right));
                logger.debug("Result: {}", result);
                break;
            }
        }
        /*for (int i = plates.size() - 1; i >= 0; i--) {
            if (this.sum(left) < this.sum(right)) {
                left.add(plates.get(i));
                logger.debug("Added to left: {}", plates.get(i));
            } else {
                right.add(plates.get(i));
                logger.debug("Added to right: {}", plates.get(i));
            }
        }*/
        //После работы алгоритма, проверка на равенство
        //Если суммы не равны, то удаляем еще один элемент и рекурсивно прогоняем.
        if (this.sum(left) != this.sum(right)) {
            this.count++;
            if (this.count >= tmp.size()) {
                System.out.println("Imposable");
                logger.debug("Result: {}", result);
                return result;
            }
            for (int i = this.count; i < tmp.size(); i++) {
                if ((this.sum(tmp) - tmp.get(i)) % 2 == 0) {
                    logger.debug("Removed element: {}", tmp.get(i));
                    tmp.remove(i);
                    break;
                }
            }
            run(tmp);
        } else {
            System.out.println(left);
            System.out.println(right);
            result = (this.sum(left) + this.sum(right));
            logger.debug("Result: {}", result);
            logger.debug(left);
        }
        logger.debug("Result: {}", result);
        logger.debug(left);
        return result;
    }

    private int sum(int[] arr) {
        return Arrays.stream(arr).reduce(0, Integer::sum);
    }

    private int sum(List<Integer> list) {
        return list.stream().reduce(0, Integer::sum);
    }

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>(Arrays.asList(19, 17, 15, 13, 11, 9, 7, 5, 3));
        System.out.println(new PlatesCalculator().run(list));
    }
}

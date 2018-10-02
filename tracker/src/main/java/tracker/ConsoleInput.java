package tracker;

import java.util.List;
import java.util.Scanner;

/**
 * Класс описывает ввод данных в консоль со стороны пользователя.
 */
public class ConsoleInput implements Input {
    private Scanner scanner = new Scanner(System.in);

    @Override
    public String ask(String question) {
        System.out.println(question);
        return scanner.nextLine();
    }

    /**
     * Метод принимает запрос и возвращает номер действия.
     *
     * @param question запрос.
     * @param range    массив возможных действий.
     * @return номер действия.
     */
    @Override
    public int ask(String question, List<Integer> range) {
        int key = Integer.valueOf(this.ask(question));
        boolean exist = false;
        for (int value : range) {
            if (value == key) {
                exist = true;
                break;
            }
        }
        if (!exist) {
            throw new MenuOutException("Out of menu range!");
        }
        return key;
    }
}


package tracker;


import java.util.List;

/**
 * Класс описывает ввод данных в консоль со стороны пользователя.
 */
public class ValidateInput implements Input {
    private boolean valid = true;
    private int value;
    private final Input input;

    public ValidateInput(final Input input) {
        this.input = input;
    }

    @Override
    public String ask(String question) {
        return this.input.ask(question);
    }

    /**
     * В методе реализована обработка исключительных ситуаций.
     * @param question запрос.
     * @param range    массив возможных действий.
     * @return номер действия.
     */
    @Override
    public int ask(String question, List<Integer> range) {
        do {
            try {
                value = this.input.ask(question, range);
                valid = false;
            } catch (MenuOutException moe) {
                System.out.println("Enter number of menu action.");
            } catch (NumberFormatException nfe) {
                System.out.println("Enter validate data.");
            }
        } while (valid);
        return value;
    }
}

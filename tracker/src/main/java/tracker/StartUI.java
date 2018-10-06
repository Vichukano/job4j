package tracker;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Main - class приложения.
 * Связывает все кдассы и обеспечивает работу приложения.
 */
public class StartUI {
    private final Input input;
    private final Tracker tracker;
    private boolean exit = false;

    public StartUI(Input input, Tracker tracker) {
        this.input = input;
        this.tracker = tracker;
        init();
    }


    /**
     * Метод реализует отрисовку меню трекера и выполнение действий в соответствии
     * с командами пользователя.
     */
    private void init() {
        MenuTracker menu = new MenuTracker(this.input, this.tracker);
        List<Integer> range = new ArrayList<>();
        menu.fillActions();
        menu.fillRange(range);
        while (!exit) {
            menu.show();
            int key = input.ask("Select: ", range);
            if (key == 8) {
                exit = true;
            }
            menu.select(key);
        }
    }


    /**
     * Точка входа в приложение.
     * @param args массив строк.
     */
    public static void main(String[] args) {
        new StartUI(new ValidateInput(new ConsoleInput()), new Tracker()).init();
    }
}

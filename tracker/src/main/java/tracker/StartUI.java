package tracker;

import java.util.ArrayList;
import java.util.List;

/**
 * Main - class приложения.
 * Связывает все кдассы и обеспечивает работу приложения.
 */
public class StartUI {
    private final Input input;
    private final Tracker tracker;

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
        for (int i = 0; i < menu.getActionsLength(); i++) {
            range.add(i);
        }

        while (!tracker.getExit()) {
            menu.show();
            menu.select(input.ask("Select: ", range));
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

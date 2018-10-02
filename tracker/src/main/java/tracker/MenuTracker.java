package tracker;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс реализует отрисовку меню трекера и действия пользлвателя.
 */
public class MenuTracker {
    private Input input;
    private Tracker tracker;
    private List<UserAction> actions = new ArrayList<>();

    public MenuTracker(Input input, Tracker tracker) {
        this.input = input;
        this.tracker = tracker;
    }

    /**
     * Метод возвращет количество доступных действий.
     *
     * @return количество доступных действий.
     */
    public int getActionsLength() {
        return this.actions.size();
    }

    /**
     * Метод заполняет массив доступными дейсвиями.
     */
    public void fillActions() {
        this.actions.add(new AddAction());
        this.actions.add(new MenuTracker.ShowAction());
        this.actions.add(new DeleteAction());
        this.actions.add(new EditAction());
        this.actions.add(new FindByIdAction());
        this.actions.add(new FindByNameAction());
        this.actions.add(new ExitAction());
    }

    /**
     * Метод реализует выбор дейсвия.
     *
     * @param key номер действия.
     */
    public void select(int key) {
        this.actions.get(key).execute(this.input, this.tracker);
    }

    /**
     * Метод выводит все достпуные действия в консоль.
     */
    public void show() {
        for (UserAction action : this.actions) {
            if (action != null) {
                System.out.println(action.info());
            }
        }
    }

    /**
     * Класс описывает добавление заявки в трекер.
     */
    private class AddAction implements UserAction {

        @Override
        public int key() {
            return 0;
        }

        /**
         * Метод выполняет добавление заявки в трекер.
         *
         * @param input   объект input.
         * @param tracker объект tracker.
         */
        @Override
        public void execute(Input input, Tracker tracker) {
            System.out.println("###### Creating new item ######");
            String name = input.ask("Введите имя заявки: ");
            String desc = input.ask("Введите описание заявки: ");
            long created = System.currentTimeMillis();
            Item item = new Item(name, desc, created);
            tracker.add(item);
            System.out.println("###### New item with id: " + item.getId() + " added ######");
            System.out.println(" ");
        }

        @Override
        public String info() {
            return "0. Add new Item";
        }
    }

    /**
     * Класс описывает вывод всех заявок, содержащихся в tracker.
     */
    private static class ShowAction implements UserAction {

        @Override
        public int key() {
            return 1;
        }

        /**
         * Метод реализует вывод всех заявок в трекере в консоль.
         *
         * @param input   объект input.
         * @param tracker объект tracker.
         */
        @Override
        public void execute(Input input, Tracker tracker) {
            System.out.println("###### List of all items ######");
            Item[] items = tracker.findAll();
            if (items.length > 0) {
                for (int i = 0; i < items.length; i++) {
                    System.out.println(items[i]);
                }
            } else {
                System.out.println("###### Tracker is empty ######");
            }
            System.out.println(" ");
        }

        @Override
        public String info() {
            return "1. Show all items";
        }
    }

    /**
     * Класс описывает удаление заявки из трекера.
     */
    private class DeleteAction implements UserAction {

        @Override
        public int key() {
            return 2;
        }

        /**
         * Метод реализует удаление заявки из трекера.
         *
         * @param input   объект input.
         * @param tracker объект tracker.
         */
        @Override
        public void execute(Input input, Tracker tracker) {
            String id = input.ask("Введите ID: ");
            if (tracker.findById(id) != null) {
                if (tracker.delete(id)) {
                    System.out.println("###### Item with id : " + id + " deleted ######");
                } else {
                    System.out.println("###### Fail ######");
                }
            } else {
                System.out.println("Заявки с таким ID не существует.");
            }
            System.out.println(" ");
        }

        @Override
        public String info() {
            return "2. Delete item";
        }
    }

    /**
     * Класс описывает вывод заявки с заданным Id.
     */
    private class FindByIdAction implements UserAction {

        @Override
        public int key() {
            return 4;
        }

        /**
         * Метод выводит в консоль заявку с заданным id.
         *
         * @param input   объект input.
         * @param tracker объект tracker.
         */
        @Override
        public void execute(Input input, Tracker tracker) {
            String id = input.ask("Введите ID: ");
            Item item = tracker.findById(id);
            if (item != null) {
                System.out.println("###### Found item ######");
                System.out.println(item);
            } else {
                System.out.println("Заявки с таким ID не найдено.");
            }
            System.out.println(" ");
        }

        @Override
        public String info() {
            return "4. Find item by Id";
        }
    }

    /**
     * Класс описывает вывод всех заявок с заданным именем.
     */
    private class FindByNameAction implements UserAction {

        @Override
        public int key() {
            return 5;
        }

        /**
         * Метод выводит в консоль все заявки с заданным именем.
         *
         * @param input   объект input.
         * @param tracker объект tracker.
         */
        @Override
        public void execute(Input input, Tracker tracker) {
            String name = input.ask("Введите имя: ");
            Item[] items = tracker.findByName(name);
            System.out.println("###### List of items with name " + name + " ######");
            for (int i = 0; i < items.length; i++) {
                System.out.println(items[i]);
            }
            System.out.println(" ");
        }

        @Override
        public String info() {
            return "5. Find items by name";
        }
    }

    /**
     * Класс описывает выход их программы.
     */
    private class ExitAction implements UserAction {

        @Override
        public int key() {
            return 6;
        }

        /**
         * Метод устанавливает флаг трекера exit в положение true.
         *
         * @param input   объект input.
         * @param tracker объект tracker.
         */
        @Override
        public void execute(Input input, Tracker tracker) {
            tracker.setExit();
        }

        @Override
        public String info() {
            return "6. Exit program";
        }
    }
}

/**
 * Класс описывает редактирование заявки.
 */
class EditAction implements UserAction {

    @Override
    public int key() {
        return 3;
    }

    /**
     * Метод реализует редактирование заявки с заданным Id.
     *
     * @param input   объект input.
     * @param tracker объект tracker.
     */
    @Override
    public void execute(Input input, Tracker tracker) {
        String id = input.ask("Введите ID: ");
        if (tracker.findById(id) != null) {
            String name = input.ask("Введите имя заявки: ");
            String desc = input.ask("Введите описание заявки: ");
            long created = System.currentTimeMillis();
            Item item = new Item(name, desc, created);
            tracker.replace(id, item);
            System.out.println("###### New item with id: " + item.getId() + " edited ######");
        } else {
            System.out.println("Заявки с таким ID не существует.");
        }
        System.out.println(" ");
    }

    @Override
    public String info() {
        return "3. Edit item";
    }
}

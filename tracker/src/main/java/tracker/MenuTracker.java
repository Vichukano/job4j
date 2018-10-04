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
        this.actions.add(new AddAction(0, "Add new item"));
        this.actions.add(new MenuTracker.ShowAction(1, "Show all items"));
        this.actions.add(new DeleteAction(2, "Delete item"));
        this.actions.add(new EditAction(3, "Edit item"));
        this.actions.add(new FindByIdAction(4, "Find item by Id"));
        this.actions.add(new FindByNameAction(5, "Find item by name"));
        this.actions.add(new AddCommentAction(6, "Add comment to item"));
        this.actions.add(new ShowAllCommentsActon(7, "Show comments to item"));
        this.actions.add(new ExitAction(8, "Exit program"));
    }

    /**
     * Метод заполняет массив с вариантами ввода действий пользователя.
     *
     * @param list массив номером действий.
     */
    public void fillRange(List<Integer> list) {
        for (int i = 0; i < getActionsLength(); i++) {
            list.add(i);
        }
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
    private class AddAction extends BaseAction {

        public AddAction(int key, String name) {
            super(key, name);
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

    }

    /**
     * Класс описывает вывод всех заявок, содержащихся в tracker.
     */
    private static class ShowAction extends BaseAction {

        public ShowAction(int key, String name) {
            super(key, name);
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
            List<Item> items = tracker.findAll();
            if (items.size() > 0) {
                for (int i = 0; i < items.size(); i++) {
                    System.out.println(items.get(i));
                }
            } else {
                System.out.println("###### Tracker is empty ######");
            }
            System.out.println(" ");
        }
    }

    /**
     * Класс описывает удаление заявки из трекера.
     */
    private class DeleteAction extends BaseAction {

        public DeleteAction(int key, String name) {
            super(key, name);
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
    }

    /**
     * Класс описывает вывод заявки с заданным Id.
     */
    private class FindByIdAction extends BaseAction {

        public FindByIdAction(int key, String name) {
            super(key, name);
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

    }

    /**
     * Класс описывает вывод всех заявок с заданным именем.
     */
    private class FindByNameAction extends BaseAction {

        public FindByNameAction(int key, String name) {
            super(key, name);
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
            List<Item> items = tracker.findByName(name);
            System.out.println("###### List of items with name " + name + " ######");
            for (int i = 0; i < items.size(); i++) {
                System.out.println(items.get(i));
            }
            System.out.println(" ");
        }

    }

    /**
     * Класс описывает выход их программы.
     */
    public class ExitAction extends BaseAction {

        public ExitAction(int key, String name) {
            super(key, name);
        }

        /**
         * Метод устанавливает флаг трекера exit в положение true.
         *
         * @param input   объект input.
         * @param tracker объект tracker.
         */
        @Override
        public void execute(Input input, Tracker tracker) {

        }
    }
}

/**
 * Класс описывает редактирование заявки.
 */
class EditAction extends BaseAction {

    public EditAction(int key, String name) {
        super(key, name);
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
}

class AddCommentAction extends BaseAction {

    public AddCommentAction(int key, String name) {
        super(key, name);
    }

    @Override
    public void execute(Input input, Tracker tracker) {
        String id = input.ask("Введите ID: ");
        String comment = input.ask("Напишите комментарий: ");
        if (!tracker.addComment(id, comment)) {
            System.out.println("Комментарий не добавлен, возможно заявки с таким ID не существует.");
        } else {
            System.out.println("Добавлен комментарий к заявке с ID: " + id);
        }
        System.out.println(" ");
    }
}

class ShowAllCommentsActon extends BaseAction {

    public ShowAllCommentsActon(int key, String name) {
        super(key, name);
    }

    @Override
    public void execute(Input input, Tracker tracker) {
        String id = input.ask("Ведите ID: ");
        List<String> comments = tracker.showComments(id);
        if (comments.size() > 0) {
            for (int i = 0; i < comments.size(); i++) {
                System.out.println(i + "." + " " + comments.get(i));
            }
            System.out.println(" ");
        }
    }
}

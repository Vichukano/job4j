package tracker;

public class StartUI {
    private static final String ADD = "0";
    private static final String SHOW = "1";
    private static final String EDIT = "2";
    private static final String DELETE = "3";
    private static final String FIND_BY_ID = "4";
    private static final String FIND_BY_NAME = "5";
    private static final String EXIT = "6";
    private final Input input;
    private final Tracker tracker;

    public StartUI(Input input, Tracker tracker) {
        this.input = input;
        this.tracker = tracker;
        init();
    }

    private void showMenu() {
        System.out.println("######## MENU ########");
        System.out.println("0. Add new Item");
        System.out.println("1. Show all items");
        System.out.println("2. Edit item");
        System.out.println("3. Delete item");
        System.out.println("4. Find item by Id");
        System.out.println("5. Find items by name");
        System.out.println("6. Exit Program");
    }

    private void createItem() {
        System.out.println("###### Creating new item ######");
        String name = input.ask("Введите имя заявки: ");
        String desc = input.ask("Введите описание заявки: ");
        long created = System.currentTimeMillis();
        Item item = new Item(name, desc, created);
        tracker.add(item);
        System.out.println("###### New item with id: " + item.getId() + " added ######");
        System.out.println(" ");
    }

    private void showAllItems() {
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

    private void editItem() {
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

    private void deleteItem() {
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

    private void findItemById() {
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

    private void findItemByName() {
        String name = input.ask("Введите имя: ");
        Item[] items = tracker.findByName(name);
        System.out.println("###### List of items with name " + name + " ######");
        for (int i = 0; i < items.length; i++) {
            System.out.println(items[i]);
        }
        System.out.println(" ");
    }

    private void init() {
        boolean exit = false;
        while (!exit) {
            this.showMenu();
            String answer = this.input.ask("Select: ");
            switch (answer) {
                case ADD:
                    createItem();
                    break;
                case SHOW:
                    showAllItems();
                    break;
                case EDIT:
                    editItem();
                    break;
                case DELETE:
                    deleteItem();
                    break;
                case FIND_BY_ID:
                    findItemById();
                    break;
                case FIND_BY_NAME:
                    findItemByName();
                    break;
                case EXIT:
                    System.out.println("###### Exit ######");
                    exit = true;
                    break;
                default:
                    break;
            }
        }
    }

    public static void main(String[] args) {
        new StartUI(new ConsoleInput(), new Tracker()).init();
    }
}

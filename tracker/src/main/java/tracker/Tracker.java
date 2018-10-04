package tracker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Класс является хранилищем заявок.
 * Описывает действия над заявками.
 */
public class Tracker {
    private List<Item> items = new ArrayList();

    /**
     * Метод добавляет Item в массив.
     * При добавлении заявки, ей присваивется уникальный id.
     * После добавления position - указатель ячейки массива сдигается на 1.
     *
     * @param item добавляемая заявка.
     * @return добавленная заявка.
     */
    public Item add(Item item) {
        item.setId(this.generateId());
        this.items.add(item);
        return item;
    }

    /**
     * Метод заменяет item в массиве с передаваемым id на передаваемый item.
     *
     * @param id   id заявки, которую нужно заменить.
     * @param item заявка, на которую будет произведена замена.
     */
    public boolean replace(String id, Item item) {
        boolean result = false;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId().equals(id)) {
                items.set(i, item);
                item.setId(id);
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * Метод удаляет item с передаваемым id из массива.
     *
     * @param id заявки, которую нужно удалить.
     */
    public boolean delete(String id) {
        boolean result = false;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId().equals(id)) {
                items.remove(i);
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * Метод возвращает массив всех item, находящихся в tracker.
     *
     * @return массив объектов item.
     */
    public List<Item> findAll() {
        List<Item> tmp = items;
        return tmp;
    }

    /**
     * Метод находит все item с переданным name.
     * В возвращаемом массиве удалены null элементы.
     *
     * @param key поле name у объекта item.
     * @return массив объектов item c переданным полем name. Если таких имен нет, то пустой массив.
     */
    public List<Item> findByName(String key) {
        List<Item> tmp = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getName().equals(key)) {
                tmp.add(items.get(i));
            }
        }
        return tmp;
    }

    /**
     * Метод находит объект item в массиве с передаваемым id и возврашает его.
     *
     * @param id id заявки.
     * @return item c заданным id. Если такой item нет, то вернет null.
     */
    public Item findById(String id) {
        Item item = null;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId().equals(id)) {
                item = items.get(i);
                break;
            }
        }
        return item;
    }

    /**
     * Метод добавляет комментарий к заявке с указанным Id.
     *
     * @param id      id заявки.
     * @param comment комментарий к заявке.
     * @return true в случае добавления комментария, иначе false.
     */
    public boolean addComment(String id, String comment) {
        boolean result = false;
        Item item = findById(id);
        if (item != null) {
            item.addComment(comment);
            result = true;
        }
        return result;
    }

    /**
     * Метод возвращает копию массива с комментариями к заявке.
     *
     * @param id id заявки.
     * @return копию массива с комментариями заявки.
     */
    public List<String> showComments(String id) {
        Item item = findById(id);
        List<String> comments = item.getComments();
        return comments;
    }

    /**
     * Метод возвращает количество объектов item, содержащихся в tracker.
     *
     * @return количество заявок.
     */
    public int getNumberOfItems() {
        return items.size();
    }

    /**
     * Метод генерирует уникальный Id для добавляемого Item.
     * Генерация происходит на основе перемножения текущего времени на
     * рандомное число.
     *
     * @return сгенерированное id.
     */
    private String generateId() {
        long time = System.currentTimeMillis();
        Random rand = new Random();
        String generatedId = String.valueOf(time * (rand.nextInt() + 1));
        return generatedId;
    }
}

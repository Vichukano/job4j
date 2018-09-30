package tracker;

import java.util.Arrays;
import java.util.Random;

public class Tracker {
    private int size = 100;
    private Item[] items = new Item[size];
    private int position = 0;

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
        this.items[position++] = item;
        return item;
    }

    /**
     * Метод заменяет item в массиве с передаваемым id на передаваемый item
     *
     * @param id   id заявки, которую нужно заменить.
     * @param item заявка, на которую будет произведена замена.
     */
    public void replace(String id, Item item) {
        Item currItem = findById(id);
        for (int i = 0; i < position; i++) {
            if (items[i].getId().equals(currItem.getId())) {
                items[i] = item;
                break;
            }
        }
    }

    /**
     * Метод удаляет item с передаваемым id из массива.
     *
     * @param id заявки, которую нужно удалить.
     */
    public void delete(String id) {
        for (int i = 0; i < size; i++) {
            if (items[i].getId().equals(id)) {
                System.arraycopy(items, i + 1, items, i, size - 1 - i);
                position--;
                break;
            }
        }
    }

    /**
     * Метод возвращает массив всех item, находящихся в tracker.
     *
     * @return массив объектов item.
     */
    public Item[] findAll() {
        Item[] tmp = Arrays.copyOf(items, position);
        return tmp;
    }

    /**
     * Метод находит все item с переданным name.
     * В возвращаемом массиве удалены null элементы.
     *
     * @param key поле name у объекта item.
     * @return массив объектов item c переданным полем name. Если таких имен нет, то пустой массив.
     */
    public Item[] findByName(String key) {
        Item[] tmp = new Item[position];
        int tmpPosition = 0;
        for (int i = 0; i < position; i++) {
            if (items[i].getName().equals(key)) {
                tmp[tmpPosition++] = items[i];
            }
        }
        Item[] tmp2 = new Item[tmpPosition];
        System.arraycopy(tmp, 0, tmp2, 0, tmpPosition);
        return tmp2;
    }

    /**
     * Метод находит объект item в массиве с передаваемым id и возврашает его.
     *
     * @param id id заявки.
     * @return item c заданным id. Если такой item нет, то вернет null.
     */
    public Item findById(String id) {
        Item item = null;
        for (int i = 0; i < position; i++) {
            if (items[i].getId().equals(id)) {
                item = items[i];
                break;
            }
        }
        return item;
    }

    /**
     * Метод возвращает количество объектов item, содержащихся в tracker.
     *
     * @return количество заявок.
     */
    public int getNumberOfItems() {
        return position;
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

package ru.job4j.todolist.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.todolist.dao.ItemDao;
import ru.job4j.todolist.dao.ItemDaoImpl;
import ru.job4j.todolist.model.Item;

import java.sql.Timestamp;
import java.util.List;

/**
 * Business logic layer service class.
 * Singleton.
 */
public class ItemService {
    private final ItemDao itemDao = new ItemDaoImpl();
    private static final ItemService INSTANCE = new ItemService();
    private static final Logger LOGGER = LogManager.getLogger(ItemService.class);

    /**
     * Default constructor.
     */
    private ItemService() {

    }

    /**
     * Method for getting instance of ItemService class.
     * @return
     */
    public static ItemService getItemServiceInstance() {
        return INSTANCE;
    }

    /**
     * Method for adding item to database.
     * @param item item for add.
     */
    public void add(Item item) {
        item.setCreated(new Timestamp(System.currentTimeMillis()));
        itemDao.add(item);
        LOGGER.debug("{} added.", item);
    }

    /**
     * Method for deleting item from database.
     * @param item item for delete.
     */
    public void delete(Item item) {
        itemDao.delete(item);
        LOGGER.debug("{} deleted", item);
    }

    /**
     * Method for updating done-value of item.
     * @param item item for update.
     */
    public void update(Item item) {
        Item tmp = itemDao.findById(item.getId());
        tmp.setDone(item.isDone());
        itemDao.update(tmp);
        LOGGER.debug("{} updated", tmp);
    }

    /**
     * Method for finding item by id.
     * @param item item object.
     * @return item with id from database.
     */
    public Item findById(Item item) {
        return itemDao.findById(item.getId());
    }

    /**
     * Method for finding all items in database.
     * @return List of items.
     */
    public List<Item> findAll() {
        return itemDao.findAll();
    }

    /**
     * Method for finding all items with done = true parameter in database.
     * @return List of items.
     */
    public List<Item> findAllDone() {
        return itemDao.findAllDone();
    }
}

package ru.job4j.todolist.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.todolist.dao.ItemDao;
import ru.job4j.todolist.dao.ItemDaoImpl;
import ru.job4j.todolist.model.Item;

import java.sql.Timestamp;
import java.util.ArrayList;
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
     *
     * @return
     */
    public static ItemService getItemServiceInstance() {
        return INSTANCE;
    }

    /**
     * Method for adding item to database.
     *
     * @param item item for add.
     */
    public void add(Item item) {
        item.setCreated(new Timestamp(System.currentTimeMillis()));
        try {
            itemDao.add(item);
            LOGGER.debug("{} added.", item);
        } catch (Exception e) {
            LOGGER.error("Failed to add item.", e);
        }
    }

    /**
     * Method for deleting item from database.
     *
     * @param item item for delete.
     */
    public void delete(Item item) {
        try {
            itemDao.delete(item);
            LOGGER.debug("{} deleted", item);
        } catch (Exception e) {
            LOGGER.error("Failed to delete item.", e);
        }
    }

    /**
     * Method for updating done-value of item.
     *
     * @param item item for update.
     */
    public void update(Item item) {
        try {
            Item tmp = itemDao.findById(item.getId());
            tmp.setDone(item.isDone());
            itemDao.update(tmp);
            LOGGER.debug("{} updated", tmp);
        } catch (Exception e) {
            LOGGER.error("Failed to update item.", e);
        }
    }

    /**
     * Method for finding item by id.
     *
     * @param item item object.
     * @return item with id from database.
     */
    public Item findById(Item item) {
        Item newItem = null;
        try {
            newItem = itemDao.findById(item.getId());
        } catch (Exception e) {
            LOGGER.error("Failed to find by id item.", e);
        }
        return newItem;
    }

    /**
     * Method for finding all items in database.
     *
     * @return List of items.
     */
    public List<Item> findAll() {
        List<Item> items = new ArrayList<>();
        try {
            items = itemDao.findAll();
        } catch (Exception e) {
            LOGGER.error("Failed to find all items.", e);
        }
        return items;
    }

    /**
     * Method for finding all items with done = true parameter in database.
     *
     * @return List of items.
     */
    public List<Item> findAllDone() {
        List<Item> items = new ArrayList<>();
        try {
            items = itemDao.findAllDone();
        } catch (Exception e) {
            LOGGER.error("Failed to find done items.", e);
        }
        return items;
    }
}

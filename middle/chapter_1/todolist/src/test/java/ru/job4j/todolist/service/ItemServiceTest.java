package ru.job4j.todolist.service;

import org.hibernate.Session;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.job4j.todolist.model.Item;
import ru.job4j.todolist.utils.HibernateSessionFactoryUtil;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ItemServiceTest {
    private final ItemService service = ItemService.getItemServiceInstance();

    @BeforeClass
    public static void setUp() {
        ItemService.getItemServiceInstance().add(new Item("test1"));
        ItemService.getItemServiceInstance().add(new Item("test2"));
        ItemService.getItemServiceInstance().add(new Item("test3"));
    }

    @AfterClass
    public static void reset() {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        session.beginTransaction();
        List<Item> items = (List<Item>) session.createQuery("from Item").list();
        for (Item item : items) {
            session.delete(item);
        }
        session.getTransaction().commit();
    }

    @Test
    public void whenFindAllThenReturnListOfItems() {
        List<Item> items = this.service.findAll();
        assertThat(items.size(), is(3));
        assertThat(items.get(0).getDesc(), is("test1"));
        assertThat(items.get(1).getDesc(), is("test2"));
        assertThat(items.get(2).getDesc(), is("test3"));
    }

    @Test
    public void whenFindByIdThenReturnItemWithId() {
        Item itemForFind = this.service.findAll().get(0);
        Item item = this.service.findById(itemForFind);
        assertThat(item.getDesc(), is("test1"));
    }

    @Test
    public void whenAddItemThenStoreIt() {
        Item addedItem = new Item("added");
        this.service.add(addedItem);
        assertThat(this.service.findAll().get(3).getDesc(), is("added"));
    }

    @Test
    public void whenFindAllDoneThenReturnListOfDoneItems() {
        List<Item> doneItems = this.service.findAllDone();
        assertThat(doneItems.size(), is(0));
    }
}

package tracker;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TrackerTest {
    private Tracker tracker;

    @Before
    public void setup() {
        tracker = new Tracker();
    }

    @Test
    public void whenReplaceNameThenReturnNewName() {
        tracker = new Tracker();
        Item previous = new Item("test1", "testDescription", 123L);
        tracker.add(previous);
        Item next = new Item("test2", "testDescription2", 1234L);
        next.setId(previous.getId());
        tracker.replace(previous.getId(), next);
        assertThat(tracker.findById(previous.getId()).getName(), is("test2"));
    }

    @Test
    public void whenAddItemPositionMustGrowUp() {
        Item item1 = new Item("1", "abc", 123L);
        Item item2 = new Item("2", "abc", 124L);
        Item item3 = new Item("3", "abc", 125L);
        tracker.add(item1);
        tracker.add(item2);
        tracker.add(item3);
        assertThat(tracker.getNumberOfItems(), is(3));
    }

    @Test
    public void whenFindAllShouldReturnAllItems() {
        Item item1 = new Item("1", "abc", 123L);
        Item item2 = new Item("2", "abc", 124L);
        Item item3 = new Item("3", "abc", 125L);
        tracker.add(item1);
        tracker.add(item2);
        tracker.add(item3);
        assertThat(tracker.findAll(), is(new Item[] {item1, item2, item3}));
    }

    @Test
    public void whenFindByNameShouldReturnAllItemsWithThatName() {
        Item item1 = new Item("1", "abc", 123L);
        Item item2 = new Item("2", "abc", 124L);
        Item item3 = new Item("2", "abc", 125L);
        Item item4 = new Item("3", "abc", 126L);
        Item item5 = new Item("2", "abc", 127L);
        tracker.add(item1);
        tracker.add(item2);
        tracker.add(item3);
        tracker.add(item4);
        tracker.add(item5);
        assertThat(tracker.findByName("2"), is(new Item[] {item2, item3, item5}));
    }

    @Test
    public void whenDeleteItemShouldRemoveItFromTrackerAndDecrementPosition() {
        Item item1 = new Item("1", "abc", 123L);
        Item item2 = new Item("2", "abc", 124L);
        Item item3 = new Item("2", "abc", 125L);
        Item item4 = new Item("3", "abc", 126L);
        Item item5 = new Item("2", "abc", 127L);
        tracker.add(item1);
        tracker.add(item2);
        tracker.add(item3);
        tracker.add(item4);
        tracker.add(item5);
        assertThat(tracker.getNumberOfItems(), is(5));
        tracker.delete(item3.getId());
        assertThat(tracker.findAll(), is(new Item[] {item1, item2, item4, item5}));
        assertThat(tracker.getNumberOfItems(), is(4));
        tracker.delete(item2.getId());
        assertThat(tracker.findAll(), is(new Item[] {item1, item4, item5}));
        assertThat(tracker.getNumberOfItems(), is(3));
        tracker.delete(item5.getId());
        assertThat(tracker.findAll(), is(new Item[] {item1, item4}));
        assertThat(tracker.getNumberOfItems(), is(2));
        tracker.delete(item1.getId());
        assertThat(tracker.findAll(), is(new Item[] {item4}));
        assertThat(tracker.getNumberOfItems(), is(1));
    }
}

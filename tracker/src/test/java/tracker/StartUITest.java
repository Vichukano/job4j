package tracker;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class StartUITest {
    private Tracker tracker;
    private Input input;

    @Before
    public void setup() {
        tracker = new Tracker();
    }

    @Test
    public void whenUserAddItemThenTrackerHasNewItemWithSameName() {
        input = new StubInput(new String[]{"0", "test name", "test desc", "6"});
        new StartUI(input, tracker);
        assertThat(tracker.findAll()[0].getName(), is("test name"));
        assertThat(tracker.findAll()[0].getDesc(), is("test desc"));
    }

    @Test
    public void whenUpdateThenTrackerHasUpdatedValue() {
        Item item = tracker.add(new Item("test name", "test desc", 123L));
        input = new StubInput(new String[]{"2", item.getId(), "test replace", "заменили заявку", "6"});
        new StartUI(input, tracker);
        assertThat(tracker.findById(item.getId()).getName(), is("test replace"));
    }

    @Test
    public void whenDeleteThenTrackerShouldDeleteThisItem() {
        Item item = tracker.add(new Item("test name", "test desc", 123L));
        Item itemDeleted = tracker.add(new Item("deleted", "will be deleted", 124L));
        input = new StubInput(new String[]{"3", itemDeleted.getId(), "6"});
        new StartUI(input, tracker);
        assertThat(tracker.findAll(), is(new Item[]{item}));
    }

    @Test
    public void whenFindItemByIdThenTrackerReturnItemWithSameId() {
        Item item1 = tracker.add(new Item("test name", "test desc", 123L));
        Item item2 = tracker.add(new Item("test name2", "test desc2", 124L));
        input = new StubInput(new String[]{"4", item2.getId(), "6"});
        new StartUI(input, tracker);
        assertThat(tracker.findById(item2.getId()), is(item2));
    }

    @Test
    public void whenFindByNameThenTrackerReturnAllItemsWithSameName() {
        Item item1 = tracker.add(new Item("test name", "test desc", 123L));
        Item item2 = tracker.add(new Item("test name", "test desc2", 124L));
        Item item3 = tracker.add(new Item("test name3", "test desc3", 125L));
        input = new StubInput(new String[]{"5", item1.getName(), "6"});
        new StartUI(input, tracker);
        assertThat(tracker.findByName(item1.getName()), is(new Item[]{item1, item2}));
    }
}

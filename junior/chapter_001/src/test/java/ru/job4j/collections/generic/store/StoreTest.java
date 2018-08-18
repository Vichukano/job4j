package ru.job4j.collections.generic.store;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class StoreTest {
    private User[] users;
    private Role[] roles;
    private UserStore userStore;
    private RoleStore roleStore;
    private User user1;
    private User user2;
    private User user3;
    private Role role1;
    private Role role2;
    private Role role3;

    @Before
    public void setUp() {
        user1 = new User("1");
        user2 = new User("2");
        user3 = new User("3");

        role1 = new Role("1");
        role2 = new Role("2");
        role3 = new Role("3");

        userStore = new UserStore(3);
        userStore.add(user1);
        userStore.add(user2);
        userStore.add(user3);

        roleStore = new RoleStore(3);
        roleStore.add(role1);
        roleStore.add(role2);
        roleStore.add(role3);
    }

    @Test
    public void whenReplaceItMustBeNewObject() {
        User user4 = new User("4");
        Role role4 = new Role("4");
        userStore.replace("2", user4);
        roleStore.replace("3", role4);
        assertThat(userStore.findById("4"), is(user4));
        assertThat(roleStore.findById("4"), is(role4));
    }

    @Test
    public void whenFindByIdMustReturnCorrectObject() {
        assertThat(userStore.findById("1"), is(user1));
        assertThat(userStore.findById("2"), is(user2));
        assertThat(userStore.findById("3"), is(user3));
        assertThat(roleStore.findById("1"), is(role1));
        assertThat(roleStore.findById("2"), is(role2));
        assertThat(roleStore.findById("3"), is(role3));
    }

    @Test(expected = NullPointerException.class)
    public void whenDeleteObjectItMustTrowsException() {
        userStore.delete("2");
        roleStore.delete("1");
        userStore.findById("2");
        roleStore.findById("1");
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void whenAddOverSizeMustTrowException() {
        userStore.add(user1);
        roleStore.add(role1);
    }

    @Test
    public void whenDeleteObjectMustReturnTrue() {
        assertThat(userStore.delete("1"), is(true));
        assertThat(roleStore.delete("2"), is(true));
    }
}

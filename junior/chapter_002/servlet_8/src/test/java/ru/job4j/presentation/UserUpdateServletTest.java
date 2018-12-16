package ru.job4j.presentation;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ru.job4j.logic.Action;
import ru.job4j.logic.Validate;
import ru.job4j.logic.ValidateService;
import ru.job4j.model.Role;
import ru.job4j.model.User;
import ru.job4j.persistent.DbStore;
import ru.job4j.persistent.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ValidateService.class, DbStore.class})
@PowerMockIgnore("javax.management.*")
public class UserUpdateServletTest {

    @Test
    public void whenAddModelToStubStoreThenStoreIt() {
        Store store = new DbStoreStub();
        Role admin = new Role("Admin");
        Role userRole = new Role("User");
        store.add(admin);
        store.add(userRole);
        Role role = store.findRoleByName("Admin");
        Role user = store.findRoleByName("User");
        assertThat(store.getRoles().size(), is(2));
        assertThat(role.getName(), is("Admin"));
        assertThat(user.getName(), is("User"));

    }

    @Test
    public void whenUpdateUserThenUpdateItInStore() throws ServletException, IOException {
        Validate validate = new ValidateStub();
        Store store = new DbStoreStub();
        PowerMockito.mockStatic(ValidateService.class);
        PowerMockito.mockStatic(DbStore.class);
        Mockito.when(ValidateService.getInstance()).thenReturn(validate);
        Mockito.when(DbStore.getInstance()).thenReturn(store);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        when(req.getParameter("action")).thenReturn("UPDATE");
        when(req.getParameter("id")).thenReturn("0");
        when(req.getParameter("login")).thenReturn("test");
        when(req.getParameter("password")).thenReturn("test");
        when(req.getParameter("email")).thenReturn("test@test.com");
        when(req.getParameter("role")).thenReturn("Admin");
        when(req.getParameter("roleName")).thenReturn("User");
        when(req.getParameter("city")).thenReturn("1");
        when(req.getParameter("country")).thenReturn("1");
        User user = new User("beforeTest", "beforeTest", "beforeTest");
        user.setId(0);
        user.setRoleName("Admin");
        Role admin = new Role("Admin");
        admin.setRoleId(1);
        Role userRole = new Role("User");
        userRole.setRoleId(2);
        store.add(admin);
        store.add(userRole);
        validate.init().action(Action.Type.ADD, user);
        assertThat(validate.findAll().size(), is(1));
        assertThat(validate.findAll().get(0).getLogin(), is("beforeTest"));
        UserUpdateServlet updateServlet = new UserUpdateServlet();
        updateServlet.doPost(req, resp);
        assertThat(validate.findAll().size(), is(1));
        assertThat(validate.findAll().get(0).getLogin(), is("test"));
        assertThat(validate.findAll().get(0).getPassword(), is("test"));
        assertThat(validate.findAll().get(0).getEmail(), is("test@test.com"));
        assertThat(validate.findAll().get(0).getRoleName(), is("Admin"));
        verify(req, times(11)).getParameter(anyString());
        verify(resp, times(1)).sendRedirect(anyString());
    }
}

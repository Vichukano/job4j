package ru.job4j.presentation;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ru.job4j.logic.Validate;
import ru.job4j.logic.ValidateService;
import ru.job4j.model.Role;
import ru.job4j.persistent.DbStore;
import ru.job4j.persistent.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ValidateService.class, DbStore.class})
@PowerMockIgnore("javax.management.*")
public class UserCreateServletTest {

    @Test
    public void whenAddUserThenStoreIt() throws ServletException, IOException {
        Validate validate = new ValidateStub();
        Store store = new DbStoreStub();
        PowerMockito.mockStatic(ValidateService.class);
        PowerMockito.mockStatic(DbStore.class);
        Mockito.when(ValidateService.getInstance()).thenReturn(validate);
        Mockito.when(DbStore.getInstance()).thenReturn(store);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        when(req.getParameter("login")).thenReturn("test");
        when(req.getParameter("password")).thenReturn("test");
        when(req.getParameter("email")).thenReturn("test@test.com");
        when(req.getParameter("role")).thenReturn("User");
        when(req.getParameter("action")).thenReturn("ADD");
        Role role = new Role("User");
        store.add(role);
        UserCreateServlet createController = new UserCreateServlet();
        createController.doPost(req, resp);
        assertThat(validate.findAll().get(0).getLogin(), is("test"));
        assertThat(validate.findAll().get(0).getPassword(), is("test"));
        assertThat(validate.findAll().get(0).getEmail(), is("test@test.com"));
        assertThat(validate.findAll().get(0).getRoleName(), is("User"));
    }
}

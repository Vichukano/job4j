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
import ru.job4j.model.User;
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
public class UserDeleteServletTest {

    @Test
    public void whenDeleteUserThenDeleteItFromStore() throws ServletException, IOException {
        Validate validate = new ValidateStub();
        Store store = new DbStoreStub();
        PowerMockito.mockStatic(ValidateService.class);
        PowerMockito.mockStatic(DbStore.class);
        Mockito.when(ValidateService.getInstance()).thenReturn(validate);
        Mockito.when(DbStore.getInstance()).thenReturn(store);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        User user = new User("test", "test", "test");
        user.setId(2);
        validate.init().action(Action.Type.ADD, user);
        assertThat(validate.findAll().size(), is(1));
        assertThat(validate.findAll().get(0).getLogin(), is("test"));
        when(req.getParameter("action")).thenReturn("DELETE");
        when(req.getParameter("id")).thenReturn("0");
        UserDeleteServlet deleteServlet = new UserDeleteServlet();
        deleteServlet.doGet(req, resp);
        assertThat(validate.findAll().size(), is(0));
    }

}

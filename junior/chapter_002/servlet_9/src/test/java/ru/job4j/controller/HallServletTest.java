package ru.job4j.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ru.job4j.persistence.DbStore;
import ru.job4j.service.PlaceService;
import ru.job4j.service.PlaceServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PlaceServiceImpl.class)
@PowerMockIgnore("javax.management.*")
public class HallServletTest {

    @Test
    public void whenGetRequestThenReturnJsonPlaces() throws IOException, ServletException {
        PlaceService placeService = new PlaceServiceImplStub();
        PowerMockito.mockStatic(PlaceServiceImpl.class);
        Mockito.when(PlaceServiceImpl.getPlaceServiceInstance()).thenReturn(placeService);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        PrintWriter writer = mock(PrintWriter.class);
        when(resp.getWriter()).thenReturn(writer);
        new HallServlet().doGet(req, resp);
        verify(resp, times(1)).getWriter();
        verify(writer, times(1))
                .print("[{\"id\":0,\"row\":1,\"col\":1,\"cost\":0.0,\"reserved\":false},"
                        + "{\"id\":0,\"row\":2,\"col\":2,\"cost\":0.0,\"reserved\":false},"
                        + "{\"id\":0,\"row\":3,\"col\":3,\"cost\":0.0,\"reserved\":false}]");
        verify(writer, times(1)).flush();
    }

}

package ru.job4j.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ru.job4j.service.CustomerService;
import ru.job4j.service.CustomerServiceImpl;
import ru.job4j.service.PlaceService;
import ru.job4j.service.PlaceServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({CustomerServiceImpl.class, PlaceServiceImpl.class})
@PowerMockIgnore("javax.management.*")
public class OrderControllerTest {

    @Test
    public void whenDoPostThenAddCustomerWithPlace() throws IOException, ServletException {
        CustomerService customerService = new CustomerServiceImplStub();
        PlaceService placeService = new PlaceServiceImplStub();
        PowerMockito.mockStatic(CustomerServiceImpl.class);
        PowerMockito.mockStatic(PlaceServiceImpl.class);
        when(CustomerServiceImpl.getCustomerServiceInstance()).thenReturn(customerService);
        when(PlaceServiceImpl.getPlaceServiceInstance()).thenReturn(placeService);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        BufferedReader reader = mock(BufferedReader.class);
        when(req.getReader()).thenReturn(reader);
        when(reader.readLine()).thenReturn(
                "{\"id\" : \"1\", "
                + "\"name\" : \"test\", "
                + "\"phone\" : \"111\", "
                + "\"placeId\" : \"1\", "
                + "\"row\" : \"1\", "
                + "\"col\" : \"1\"}")
                .thenReturn(null);
        new OrderController().doPost(req, resp);
        verify(req, times(1)).getReader();
    }
}

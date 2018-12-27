package ru.job4j.controller;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
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
import java.io.IOException;
import java.io.PrintWriter;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
@PrepareForTest(CustomerServiceImpl.class)
@PowerMockIgnore("javax.management.*")
public class CustomersServletTest {

    @Test
    public void whenPostRequestThenReturnJsonCustomers() throws IOException, ServletException {
        CustomerService customerService = new CustomerServiceImplStub();
        PowerMockito.mockStatic(CustomerServiceImpl.class);
        Mockito.when(CustomerServiceImpl.getCustomerServiceInstance()).thenReturn(customerService);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        PrintWriter writer = mock(PrintWriter.class);
        when(resp.getWriter()).thenReturn(writer);
        new CustomersServlet().doPost(req, resp);
        verify(resp, times(1)).getWriter();
        verify(writer, times(1))
                .print("[{\"id\":0,\"name\":\"test\",\"phone\":\"test\",\"placeId\":0,\"row\":0,\"col\":0},"
                        + "{\"id\":0,\"name\":\"test\",\"phone\":\"test\",\"placeId\":0,\"row\":0,\"col\":0},"
                        + "{\"id\":0,\"name\":\"test\",\"phone\":\"test\",\"placeId\":0,\"row\":0,\"col\":0}]");
        verify(writer, times(1)).flush();
    }
}

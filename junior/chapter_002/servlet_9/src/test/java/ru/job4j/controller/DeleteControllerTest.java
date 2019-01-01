package ru.job4j.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ru.job4j.entity.Customer;
import ru.job4j.service.CustomerService;
import ru.job4j.service.CustomerServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(CustomerServiceImpl.class)
@PowerMockIgnore("javax.management.*")
public class DeleteControllerTest {

    @Test
    public void whenPostRequestThenDeleteCustomer() throws IOException, ServletException {
        CustomerService customerService = mock(CustomerServiceImplStub.class);
        when(customerService.deleteCustomerWithPlace(any(Customer.class))).thenReturn(true);
        PowerMockito.mockStatic(CustomerServiceImpl.class);
        Mockito.when(CustomerServiceImpl.getCustomerServiceInstance()).thenReturn(customerService);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        BufferedReader reader = mock(BufferedReader.class);
        when(req.getReader()).thenReturn(reader);
        when(reader.readLine()).thenReturn("1").thenReturn(null);
        new DeleteController().doPost(req, resp);
        verify(req, times(1)).getReader();
        verify(customerService, times(1)).deleteCustomerWithPlace(any(Customer.class));
    }

    @Test
    public void whenPostRequestThenDeleteCustomerWithoutMock() throws IOException, ServletException {
        CustomerService customerService = new CustomerServiceImplStub();
        PowerMockito.mockStatic(CustomerServiceImpl.class);
        Mockito.when(CustomerServiceImpl.getCustomerServiceInstance()).thenReturn(customerService);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        BufferedReader reader = mock(BufferedReader.class);
        when(req.getReader()).thenReturn(reader);
        when(reader.readLine()).thenReturn("2").thenReturn(null);
        new DeleteController().doPost(req, resp);
        verify(req, times(1)).getReader();
    }
}

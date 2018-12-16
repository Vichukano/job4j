package ru.job4j.presentation;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ru.job4j.model.City;
import ru.job4j.model.Country;
import ru.job4j.persistent.DbStore;
import ru.job4j.persistent.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
@PrepareForTest(DbStore.class)
@PowerMockIgnore("javax.management.*")
public class CitiesControllerTest {
    private Store store = new DbStoreStub();

    @Test
    public void whenGetCountriesThenReturnArrayList() {
        assertThat(store.getCountries(), is(new ArrayList<>(Arrays.asList(
                new Country(1, "Russia"),
                new Country(2, "USA"),
                new Country(3, "Narnia")))));
    }

    @Test
    public void whenGetCitiesThenReturnArrayList() {
        assertThat(store.getCitiesByCountryId(0).size(), is(3));
        assertThat(store.getCitiesByCountryId(0).get(0).getName(), is("GUS"));
        assertThat(store.getCitiesByCountryId(0).get(1).getName(), is("Vladimir"));
        assertThat(store.getCitiesByCountryId(0).get(2).getName(), is("Murom"));
    }

    @Test
    public void whenGetRequestThenReturnJsonCountries() throws IOException, ServletException {
        Store store = new DbStoreStub();
        PowerMockito.mockStatic(DbStore.class);
        Mockito.when(DbStore.getInstance()).thenReturn(store);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        PrintWriter writer = mock(PrintWriter.class);
        when(resp.getWriter()).thenReturn(writer);
        new CitiesController().doGet(req, resp);
        verify(resp, times(1)).getWriter();
        verify(writer, times(1))
                .print("[{\"id\":1,\"name\":\"Russia\"},{\"id\":2,\"name\":\"USA\"},{\"id\":3,\"name\":\"Narnia\"}]");
        verify(writer, times(1)).flush();
    }

    @Test
    public void whenPostRequestThenReturnJsonCities() throws IOException, ServletException {
        Store store = new DbStoreStub();
        PowerMockito.mockStatic(DbStore.class);
        Mockito.when(DbStore.getInstance()).thenReturn(store);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        BufferedReader reader = mock(BufferedReader.class);
        when(req.getReader()).thenReturn(reader);
        when(reader.readLine()).thenReturn("1").thenReturn(null);
        PrintWriter writer = mock(PrintWriter.class);
        when(resp.getWriter()).thenReturn(writer);
        new CitiesController().doPost(req, resp);
        verify(resp, times(1)).getWriter();
        verify(writer, times(1))
                .print("[{\"id\":1,\"name\":\"GUS\"},{\"id\":2,\"name\":\"Vladimir\"},{\"id\":3,\"name\":\"Murom\"}]");
        verify(writer, times(1)).flush();
    }
}

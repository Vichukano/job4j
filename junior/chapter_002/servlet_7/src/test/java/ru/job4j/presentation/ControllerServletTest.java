package ru.job4j.presentation;


import org.junit.Test;
import ru.job4j.persistent.MemoryStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ControllerServletTest {

    @Test
    public void whenRequestDataThenResponseIt() throws IOException, ServletException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        BufferedReader reader = mock(BufferedReader.class);
        PrintWriter writer = mock(PrintWriter.class);
        when(req.getReader()).thenReturn(reader);
        when(reader.readLine())
                .thenReturn("{\"name\":\"test2\",\"surname\":\"test\",\"sex\":\"Male\",\"desc\":\"test\"}")
                .thenReturn(null);
        when(resp.getWriter()).thenReturn(writer);
        ControllerServlet controllerServlet = new ControllerServlet();
        controllerServlet.doPost(req, resp);
        assertThat(MemoryStore.getInstance().findAll().size(), is(1));
        verify(req, times(1)).getReader();
        verify(resp, times(1)).getWriter();
        verify(writer, times(1)).append(anyString());
    }
}

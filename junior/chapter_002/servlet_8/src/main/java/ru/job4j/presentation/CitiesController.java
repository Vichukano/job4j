package ru.job4j.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.model.City;
import ru.job4j.model.Country;
import ru.job4j.persistent.DbStore;
import ru.job4j.persistent.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class CitiesController extends HttpServlet {
    private final Store store = DbStore.getInstance();
    private final static Logger LOG = LogManager.getLogger(CitiesController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        List<Country> countries = store.getCountries();
        ObjectMapper mapper = new ObjectMapper();
        String jsonData = mapper.writeValueAsString(countries);
        LOG.debug(jsonData);
        PrintWriter writer = resp.getWriter();
        writer.print(jsonData);
        writer.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        BufferedReader reader = req.getReader();
        StringBuilder sb = new StringBuilder();
        String data;
        while ((data = reader.readLine()) != null) {
            sb.append(data);
        }
        ObjectMapper mapper = new ObjectMapper();
        List<City> cities = store.getCitiesByCountryId(Integer.parseInt(sb.toString()));
        String jsonData = mapper.writeValueAsString(cities);
        LOG.debug(jsonData);
        PrintWriter writer = resp.getWriter();
        writer.print(jsonData);
        writer.flush();
    }
}

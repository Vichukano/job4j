package ru.job4j.todolist.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.todolist.model.Item;
import ru.job4j.todolist.service.ItemService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Servlet-class for getting and adding items.
 * Mapping "/"
 */
public class MainController extends HttpServlet {
    private final ItemService service = ItemService.getItemServiceInstance();
    private static final Logger LOGGER = LogManager.getLogger(MainController.class);

    /**
     * Method for getting all items to client.
     *
     * @param req  client request.
     * @param resp server response.
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        List<Item> items = this.service.findAll();
        LOGGER.debug("All items from DB: {}", items);
        String jsonItems = new ObjectMapper().writeValueAsString(items);
        LOGGER.debug("Items in JSON format {}", jsonItems);
        PrintWriter writer = resp.getWriter();
        writer.print(jsonItems);
        writer.flush();
    }

    /**
     * Method for adding items to database.
     *
     * @param req  client request - json data for add.
     * @param resp server response - added item.
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        BufferedReader reader = req.getReader();
        StringBuilder sb = new StringBuilder();
        String data;
        while ((data = reader.readLine()) != null) {
            sb.append(data);
        }
        Item item = new ObjectMapper().readValue(sb.toString(), Item.class);
        this.service.add(item);
        LOGGER.debug("{} item added in servlet.", item);
        String jsonItem = new ObjectMapper().writeValueAsString(item);
        PrintWriter writer = resp.getWriter();
        writer.print(jsonItem);
        writer.flush();
    }
}

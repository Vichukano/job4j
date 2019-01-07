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
 * Servlet - class for getting done items and updating done - value of item.
 * Mapping "/done"
 */
public class DoneController extends HttpServlet {
    private final ItemService service = ItemService.getItemServiceInstance();
    private static final Logger LOGGER = LogManager.getLogger(DoneController.class);

    /**
     * Method for getting done items to client.
     *
     * @param req  client request.
     * @param resp server response - json data of items with done = true parameter.
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        List<Item> completedItems = this.service.findAllDone();
        String jsonCompleted = new ObjectMapper().writeValueAsString(completedItems);
        LOGGER.debug("Completed items: {}", jsonCompleted);
        PrintWriter writer = resp.getWriter();
        writer.print(jsonCompleted);
        writer.flush();
    }

    /**
     * Method for update item done - value.
     *
     * @param req  client request - json data of id item for update.
     * @param resp server response - json data of updated item.
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
        this.service.update(item);
        LOGGER.debug("{} item updated in servlet.", item);
        String jsonItem = new ObjectMapper().writeValueAsString(item);
        PrintWriter writer = resp.getWriter();
        writer.print(jsonItem);
        writer.flush();
    }
}

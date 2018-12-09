package ru.job4j.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.model.User;
import ru.job4j.persistent.MemoryStore;
import ru.job4j.persistent.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Controller servlet class.
 * Take json data from client, create user based on jason,
 * and store it in MemoryStore.
 */
public class ControllerServlet extends HttpServlet {
    private final static Logger LOG = LogManager.getLogger(ControllerServlet.class);
    private final Store store = MemoryStore.getInstance();

    /**
     * doPost method.
     *
     * @param req  client request.
     * @param resp servlet response.
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
        LOG.debug(sb.toString());
        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.readValue(sb.toString(), User.class);
        store.add(user);
        LOG.debug(user.toString());
        String jsonData = mapper.writeValueAsString(user);
        LOG.debug(jsonData);
        PrintWriter writer = resp.getWriter();
        writer.append(jsonData);
        writer.flush();
    }
}

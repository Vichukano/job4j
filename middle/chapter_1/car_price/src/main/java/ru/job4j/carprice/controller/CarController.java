package ru.job4j.carprice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.carprice.model.*;
import ru.job4j.carprice.service.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Servlet for getting and adding car objects to database.
 */
public class CarController extends HttpServlet {
    private final Logger logger = LogManager.getLogger(CarController.class);
    private final CarService carService = CarService.getInstance();
    private final CarBodyService bodyService = CarBodyService.getInstance();
    private final EngineService engineService = EngineService.getInstance();
    private final TransmissionService trService = TransmissionService.getInstance();
    private final UserService userService = UserService.getInstance();
    private final Random random = new Random();

    /**
     * Method for getting car objects from database
     * and sending it to client in JSON format.
     *
     * @param req  client request.
     * @param resp server response - car objects in JSON format.
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        List<Car> cars = this.carService.findAll();
        String jsonCars = new ObjectMapper().writeValueAsString(cars);
        logger.debug("Cars in JSON: {}", jsonCars);
        PrintWriter writer = resp.getWriter();
        writer.print(jsonCars);
        writer.flush();
        writer.close();
    }

    /**
     * Method for building car object with client parameters
     * and persist it. All parameters add to map reqParams.
     * If image not sending, then create empty image with url "empty".
     *
     * @param req  client request with car parameters.
     * @param resp servet response.
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        Map<String, String> reqParams = new HashMap<>();
        Image image = null;
        try {
            ServletFileUpload fileUpload = new ServletFileUpload(new DiskFileItemFactory());
            List<FileItem> fileItems = fileUpload.parseRequest(req);
            for (FileItem item : fileItems) {
                if (item.isFormField()) {
                    reqParams.put(item.getFieldName(), item.getString());
                } else {
                    if (!item.getName().equals("")) {
                        logger.debug("Name of image file: {}", item.getName());
                        String url = getServletContext().getInitParameter("ImageSrc")
                                + random.nextInt(1000)
                                + item.getName();
                        item.write(new File(url));
                        image = new Image();
                        image.setUrl(url);
                        logger.debug(url);
                    } else {
                        image = new Image("empty");
                        logger.debug("Image not found!");
                    }
                }
            }
            logger.debug(reqParams.entrySet());
            CarBody body = this.bodyService.findById(Long.parseLong(reqParams.get("body")));
            Engine engine = this.engineService.findById(Long.parseLong(reqParams.get("engine")));
            Transmission tr = trService.findById(Long.parseLong(reqParams.get("transmission")));
            long id = Long.parseLong(String.valueOf(session.getAttribute("id")));
            User user = this.userService.findById(id);
            Car car = new Car(
                    reqParams.get("name"),
                    Double.parseDouble(reqParams.get("price")),
                    reqParams.get("color"),
                    body,
                    engine,
                    tr,
                    Integer.parseInt(reqParams.get("mileage"))
            );
            car.setDescription(reqParams.get("desc"));
            car.setUser(user);
            if (image != null) {
                car.setImage(image);
            }
            this.carService.add(car);
            logger.debug("User with login {} add new car", session.getAttribute("login"));
            logger.debug("Car from client: {}", car);
        } catch (Exception e) {
            logger.error("Cannot upload file.", e);
        }
        resp.sendRedirect("index.html");
    }
}


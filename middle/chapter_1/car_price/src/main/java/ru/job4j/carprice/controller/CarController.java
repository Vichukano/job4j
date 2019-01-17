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
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarController extends HttpServlet {
    private final Logger logger = LogManager.getLogger(CarController.class);
    private final CarService carService = CarService.getInstance();
    private final CarBodyService bodyService = CarBodyService.getInstance();
    private final EngineService engineService = EngineService.getInstance();
    private final TransmissionService trService = TransmissionService.getInstance();

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
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Map<String, String> reqParams = new HashMap<>();
        List<Image> images = new ArrayList<>();
        try {
            ServletFileUpload fileUpload = new ServletFileUpload(new DiskFileItemFactory());
            List<FileItem> fileItems = fileUpload.parseRequest(req);
            for (FileItem item : fileItems) {
                if (item.isFormField()) {
                    //Пробегает по всем полям формы.
                    //Что-то нужно придумать!
                    reqParams.put(item.getFieldName(), item.getString());
                } else {
                    //Здесь работа с приложенными изображениями.
                    String url = "C:\\projects\\job4j\\middle\\chapter_1\\car_price\\upload\\" + item.getName();
                    item.write(new File(url));
                    Image image = new Image();
                    image.setUrl(url);
                    images.add(image);
                    logger.debug(url);
                }
            }
            logger.debug(reqParams.entrySet());
            CarBody body = this.bodyService.findById(Long.parseLong(reqParams.get("body")));
            Engine engine = this.engineService.findById(Long.parseLong(reqParams.get("engine")));
            Transmission tr = trService.findById(Long.parseLong(reqParams.get("transmission")));
            Car car = new Car(
                    reqParams.get("name"),
                    Double.parseDouble(reqParams.get("price")),
                    reqParams.get("color"),
                    body,
                    engine,
                    tr,
                    Integer.parseInt(reqParams.get("mileage")),
                    reqParams.get("desc")
            );
            for (Image image : images) {
                car.getImages().add(image);
            }
            this.carService.add(car);
            logger.debug("Car from client: {}", car);
        } catch (Exception e) {
            logger.error("Cannot upload file.", e);
        }
        req.getRequestDispatcher("index.html").forward(req, resp);
    }
}


package ru.job4j.carprice.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.carprice.model.Car;
import ru.job4j.carprice.model.CarBody;
import ru.job4j.carprice.model.Engine;
import ru.job4j.carprice.model.Transmission;
import ru.job4j.carprice.service.CarBodyService;
import ru.job4j.carprice.service.CarService;
import ru.job4j.carprice.service.EngineService;
import ru.job4j.carprice.service.TransmissionService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
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


        ServletFileUpload fileUpload = new ServletFileUpload(new DiskFileItemFactory());
        try {
            List<FileItem> fileItems = fileUpload.parseRequest(req);
            for (FileItem item : fileItems) {
                if (item.isFormField()) {
                    String model = item.getString();
                    //Пробегает по всем полям формы.
                    //Что-то нужно придумать!
                    logger.debug(model);
                } else {
                    String url = "C:\\projects\\job4j\\middle\\chapter_1\\car_price\\upload\\" + item.getName();
                    item.write(new File(url));
                    logger.debug(url);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }
}


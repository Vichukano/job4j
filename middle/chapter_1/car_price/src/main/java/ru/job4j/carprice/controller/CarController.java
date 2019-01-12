package ru.job4j.carprice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.carprice.model.Car;
import ru.job4j.carprice.service.CarService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class CarController extends HttpServlet {
    private final Logger logger = LogManager.getLogger(CarController.class);
    private final CarService service = CarService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        List<Car> cars = this.service.findAll();
        String jsonCars = new ObjectMapper().writeValueAsString(cars);
        logger.debug("Cars in JSON: {}", jsonCars);
        PrintWriter writer = resp.getWriter();
        writer.print(jsonCars);
        writer.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        BufferedReader reader = req.getReader();
        StringBuilder sb = new StringBuilder();
        String data;
        while ((data = reader.readLine()) != null) {
            sb.append(data);
        }
        logger.debug("Incoming JSON from client: {}", sb.toString());
        Car car = new ObjectMapper().readValue(sb.toString(), Car.class);
        logger.debug("From JSON to CAR: {}", car.toString());
        this.service.add(car);
    }
}

package ru.job4j.carprice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.carprice.model.Car;
import ru.job4j.carprice.model.CarBody;
import ru.job4j.carprice.model.Engine;
import ru.job4j.carprice.model.Transmission;
import ru.job4j.carprice.service.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class UpdateController extends HttpServlet {
    private final Logger logger = LogManager.getLogger(UpdateController.class);
    private final CarService carService = CarService.getInstance();
    private final CarBodyService bodyService = CarBodyService.getInstance();
    private final EngineService engineService = EngineService.getInstance();
    private final TransmissionService trService = TransmissionService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        String id = req.getParameter("id");
        logger.debug("Car id from client: S{}", id);
        Car car = new Car();
        car.setId(Long.parseLong(id));
        Car found = this.carService.findById(car);
        String jsonCar = new ObjectMapper().writeValueAsString(found);
        logger.debug("Found car in json: {}", jsonCar);
        PrintWriter writer = resp.getWriter();
        writer.print(jsonCar);
        writer.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Long.parseLong(req.getParameter("carId"));
        logger.debug("Car id for update: {}", id);
        String model = req.getParameter("name");
        Double price = Double.parseDouble(req.getParameter("price"));
        String color = req.getParameter("color");
        int mileage = Integer.parseInt(req.getParameter("mileage"));
        CarBody body = this.bodyService.findById(Long.parseLong(req.getParameter("body")));
        Engine engine = this.engineService.findById(Long.parseLong(req.getParameter("engine")));
        Transmission tr = trService.findById(Long.parseLong(req.getParameter("transmission")));
        String desc = req.getParameter("desc");
        Car car = new Car();
        car.setId(id);
        Car found = this.carService.findById(car);
        found.setSold(false);
        if (req.getParameter("sold") != null) {
            found.setSold(true);
        }
        found.setName(model);
        found.setPrice(price);
        found.setColor(color);
        found.setMileage(mileage);
        found.setBody(body);
        found.setEngine(engine);
        found.setTransmission(tr);
        found.setDescription(desc);
        this.carService.update(found);
        logger.debug("Car updated: {}", found);
        req.getRequestDispatcher("/cars").forward(req, resp);
    }
}

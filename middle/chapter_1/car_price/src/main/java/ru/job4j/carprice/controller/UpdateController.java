package ru.job4j.carprice.controller;

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

public class UpdateController extends HttpServlet {
    private final Logger logger = LogManager.getLogger(UpdateController.class);
    private final CarService carService = CarService.getInstance();
    private final CarBodyService bodyService = CarBodyService.getInstance();
    private final EngineService engineService = EngineService.getInstance();
    private final TransmissionService trService = TransmissionService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("update.html").forward(req, resp);

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

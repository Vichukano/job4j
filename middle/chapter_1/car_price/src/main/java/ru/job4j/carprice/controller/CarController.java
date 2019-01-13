package ru.job4j.carprice.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
        resp.setContentType("application/json");
        BufferedReader reader = req.getReader();
        StringBuilder sb = new StringBuilder();
        String data;
        while ((data = reader.readLine()) != null) {
            sb.append(data);
        }
        logger.debug("Incoming JSON from client: {}", sb.toString());
        ObjectMapper mapper = new ObjectMapper();
        //Конвертируем JSON в map, можно вызывать атрибуды по ключу и собирать объекты.
        Map<String, String> jsonMap = mapper.readValue(sb.toString(), HashMap.class);
        CarBody body = this.bodyService.findById(Long.parseLong(jsonMap.get("body")));
        Engine engine = this.engineService.findById(Long.parseLong(jsonMap.get("engine")));
        Transmission tr = this.trService.findById(Long.parseLong(jsonMap.get("transmission")));
        Car car = new Car(
                jsonMap.get("name"),
                Double.parseDouble(jsonMap.get("price")),
                jsonMap.get("color"),
                body,
                engine,
                tr
        );
        logger.debug("Completed car: {}", car.toString());
        this.carService.add(car);
    }
}

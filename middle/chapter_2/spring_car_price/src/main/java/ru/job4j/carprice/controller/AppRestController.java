package ru.job4j.carprice.controller;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.carprice.model.*;
import ru.job4j.carprice.service.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController
public class AppRestController {
    private final CarService carService;
    private final CarBodyService carBodyService;
    private final EngineService engineService;
    private final TransmissionService transmissionService;
    private final ImageService imageService;
    private final UserService userService;
    private final Random random = new Random();
    private final Logger logger = LogManager.getLogger(AppRestController.class);
    private final String url = "/upload/images/";

    @Autowired
    public AppRestController(CarService carService, CarBodyService carBodyService, EngineService engineService, TransmissionService transmissionService, ImageService imageService, UserService userService) {
        this.carService = carService;
        this.carBodyService = carBodyService;
        this.engineService = engineService;
        this.transmissionService = transmissionService;
        this.imageService = imageService;
        this.userService = userService;
    }

    @GetMapping(value = "/api/cars")
    public List<Car> getAllCars(HttpServletRequest req) {
        List<Car> cars;
        if (req.getParameter("action") != null) {
            Action.Type action = Action.Type.valueOf(req.getParameter("action").toUpperCase());
            logger.debug("Action is: {}", action.toString());
            cars = this.carService.init().action(action);
        } else {
            String query = req.getParameter("query");
            String type = req.getParameter("type");
            logger.debug("Query: {}, type: {}", query, type);
            cars = this.carService.findCarByPart(query, type);
        }
        logger.debug("Cars from client: {}", cars);
        return cars;
    }

    @PostMapping(value = "/api/cars")
    public void addCar(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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
                        String url = this.url
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
            CarBody body = this.carBodyService.findById(Long.parseLong(reqParams.get("body")));
            Engine engine = this.engineService.findById(Long.parseLong(reqParams.get("engine")));
            Transmission tr = transmissionService.findById(Long.parseLong(reqParams.get("transmission")));
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
        resp.sendRedirect("/");
    }

    @GetMapping(value = "/api/body")
    public List<CarBody> getCarBodies() {
        List<CarBody> bodies = this.carBodyService.findAll();
        logger.debug("Car bodies: {} ", bodies);
        return bodies;
    }

    @GetMapping(value = "/api/engine")
    public List<Engine> getCarEngines() {
        List<Engine> engines = this.engineService.findAll();
        logger.debug("Car engines: {}", engines);
        return engines;
    }

    @GetMapping(value = "/api/transmission")
    public List<Transmission> getCarTransmissions() {
        List<Transmission> transmissions = this.transmissionService.findAll();
        logger.debug("Car transmissions: {}", transmissions);
        return transmissions;
    }

    @GetMapping(value = "/api/image/*")
    public void loadImage(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("image/jpg");
        String path = req.getRequestURI();
        logger.debug(path);
        ServletOutputStream outStream;
        outStream = resp.getOutputStream();
        FileInputStream fin = new FileInputStream(
                this.url
                + path.substring("/image/".length()));
        BufferedInputStream bin = new BufferedInputStream(fin);
        BufferedOutputStream bout = new BufferedOutputStream(outStream);
        int data;
        while ((data = bin.read()) != -1) {
            bout.write(data);
        }
        bin.close();
        fin.close();
        bout.close();
        outStream.close();
    }

    @GetMapping(value = "/api/update")
    public Car getCarForUpdate(HttpServletRequest req) {
        String id = req.getParameter("id");
        logger.debug("Car id from client: {}", id);
        Car car = new Car();
        car.setId(Long.parseLong(id));
        Car found = this.carService.findById(car);
        return found;
    }

    @PostMapping(value = "/api/update")
    public void updateCar(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long id = Long.parseLong(req.getParameter("carId"));
        logger.debug("Car id for update: {}", id);
        String model = req.getParameter("name");
        Double price = Double.parseDouble(req.getParameter("price"));
        String color = req.getParameter("color");
        int mileage = Integer.parseInt(req.getParameter("mileage"));
        CarBody body = this.carBodyService.findById(Long.parseLong(req.getParameter("body")));
        Engine engine = this.engineService.findById(Long.parseLong(req.getParameter("engine")));
        Transmission tr = transmissionService.findById(Long.parseLong(req.getParameter("transmission")));
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
        resp.sendRedirect("/");
    }


}

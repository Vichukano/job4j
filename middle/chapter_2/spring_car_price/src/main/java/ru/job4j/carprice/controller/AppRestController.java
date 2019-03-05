package ru.job4j.carprice.controller;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.carprice.dto.FormDto;
import ru.job4j.carprice.model.*;
import ru.job4j.carprice.service.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Main controller of application.
 */
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

    @Autowired
    ServletContext context;

    @Autowired
    public AppRestController(CarService carService, CarBodyService carBodyService,
                             EngineService engineService, TransmissionService transmissionService,
                             ImageService imageService, UserService userService) {
        this.carService = carService;
        this.carBodyService = carBodyService;
        this.engineService = engineService;
        this.transmissionService = transmissionService;
        this.imageService = imageService;
        this.userService = userService;
    }

    /**
     * Method for sending list of cars entities in json
     * format to the view.
     *
     * @param action value of action in String format.
     * @param query  value of NamedQuery in String format.
     * @param type   value of type for NamedQuery in String format.
     * @return list of car entities in jason format.
     */
    @GetMapping(value = "/api/cars")
    public List<Car> getAllCars(@RequestParam(required = false) String action,
                                @RequestParam(required = false) String query,
                                @RequestParam(required = false) String type) {
        List<Car> cars;
        if (action != null) {
            Action.Type actionType = Action.Type.valueOf(
                    action.toUpperCase()
            );
            logger.debug("Action is: {}", actionType.toString());
            cars = this.carService.init().action(actionType);
        } else {
            logger.debug("Query: {}, type: {}", query, type);
            cars = this.carService.findCarByPart(query, type);
        }
        logger.debug("Cars from client: {}", cars);
        return cars;
    }

    /**
     * Method for adding car.
     *
     * @param req  HttpServletRequest
     * @param resp HttpServletResponse
     * @throws IOException
     */
    @PostMapping(value = "/api/cars")
    public void addCar(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        HttpSession session = req.getSession();
        Map<String, String> reqParams = new HashMap<>();
        Image image = null;
        try {
            logger.debug("In addCar try block");
            ServletFileUpload fileUpload = new ServletFileUpload(new DiskFileItemFactory());
            List<FileItem> fileItems = fileUpload.parseRequest(req);
            for (FileItem item : fileItems) {
                if (item.isFormField()) {
                    reqParams.put(item.getFieldName(), item.getString());
                } else {
                    if (!item.getName().equals("")) {
                        logger.debug("Name of image file: {}", item.getName());
                        String url =
                                this.context.getInitParameter("ImageSrc")
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
            logger.debug("User with login {} add new car",
                    session.getAttribute("login"));
            logger.debug("Car from client: {}", car);
        } catch (Exception e) {
            logger.error("Cannot upload file.", e);
        }
        resp.sendRedirect("/");
    }

    /**
     * Method return CarBody entities in json format to view.
     *
     * @return List of CarBody entities in json format.
     */
    @GetMapping(value = "/api/body")
    public List<CarBody> getCarBodies() {
        List<CarBody> bodies = this.carBodyService.findAll();
        logger.debug("Car bodies: {} ", bodies);
        return bodies;
    }

    /**
     * Method return Engine entities in json format to view.
     *
     * @return List of Engine entities in json format.
     */
    @GetMapping(value = "/api/engine")
    public List<Engine> getCarEngines() {
        List<Engine> engines = this.engineService.findAll();
        logger.debug("Car engines: {}", engines);
        return engines;
    }

    /**
     * Method return Transmission entities in json format to view.
     *
     * @return List of Transmission entities in json format.
     */
    @GetMapping(value = "/api/transmission")
    public List<Transmission> getCarTransmissions() {
        List<Transmission> transmissions = this.transmissionService.findAll();
        logger.debug("Car transmissions: {}", transmissions);
        return transmissions;
    }

    /**
     * Method for loading image to view.
     *
     * @param req  HttpServletRequest
     * @param resp HttpServletResponse
     * @throws IOException
     */
    @GetMapping(value = "/api/image/**")
    public void loadImage(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setContentType("image/jpg");
        String path = req.getRequestURI();
        logger.debug(path);
        if ((path.substring("/api/image/".length()).equals("empty"))) {
            logger.debug("EMPTY");
            return;
        }
        ServletOutputStream outStream = null;
        FileInputStream fin = null;
        BufferedInputStream bin = null;
        BufferedOutputStream bout = null;
        try {
            outStream = resp.getOutputStream();
            fin = new FileInputStream(
                    this.context.getInitParameter("ImageSrc")
                            + path.substring("/api/image/".length())
            );
            bin = new BufferedInputStream(fin);
            bout = new BufferedOutputStream(outStream);
            int data;
            while ((data = bin.read()) != -1) {
                bout.write(data);
            }
        } catch (IOException e) {
            logger.error("IO exception: {}", e.getMessage());
            throw e;
        } finally {
            if (bout != null) {
                bout.close();
            }
            if (bin != null) {
                bin.close();
            }
            if (fin != null) {
                fin.close();
            }
            if (outStream != null) {
                outStream.close();
            }
        }
    }

    /**
     * Method get id of car for update in request param,
     * found car with this id in database and
     * sent it to client in json format.
     *
     * @param id id of car for update.
     * @return car in json format.
     */
    @GetMapping(value = "/api/update")
    public Car getCarForUpdate(@RequestParam String id) {
        logger.debug("Car id from client: {}", id);
        Car car = new Car();
        car.setId(Long.parseLong(id));
        return this.carService.findById(car);
    }

    /**
     * Method for update car params.
     * Get request params from client and transfer it
     * to FormDto object(POJO). After update redirect to
     * index.html page.
     *
     * @throws IOException
     */
    @PostMapping(value = "/api/update")
    public void updateCar(FormDto dto, HttpServletResponse resp)
            throws IOException {
        logger.debug("Car id for update: {}", dto.getCarId());
        CarBody carBody = this.carBodyService.findById(dto.getBody());
        Engine carEngine = this.engineService.findById(dto.getEngine());
        Transmission tr = transmissionService.findById(dto.getTransmission());
        Car car = new Car();
        car.setId(dto.getCarId());
        Car found = this.carService.findById(car);
        found.setSold(true);
        if (dto.getSold().equals("on sale")) {
            found.setSold(false);
        }
        found.setName(dto.getName());
        found.setPrice(dto.getPrice());
        found.setColor(dto.getColor());
        found.setMileage(dto.getMileage());
        found.setBody(carBody);
        found.setEngine(carEngine);
        found.setTransmission(tr);
        found.setDescription(dto.getDesc());
        this.carService.update(found);
        logger.debug("Car updated: {}", found);
        resp.sendRedirect("/");
    }

    /**
     * Method invalidate current session and
     * redirect to login.html page.
     *
     * @param req  HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @GetMapping(value = "/api/login")
    public void logout(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        session.invalidate();
        resp.sendRedirect("/login");
    }

    /**
     * Method for log in user to application.
     * If user login and password is credential
     * than set login and id attributes to session.
     * Only user who create car can update it.
     * Else redirect to login.html page.
     *
     * @param req      HttpServletRequest
     * @param resp     HttpServletResponse
     * @param login    user login
     * @param password user password
     * @throws IOException
     */
    @PostMapping(value = "/api/login")
    public void login(HttpServletRequest req,
                      HttpServletResponse resp,
                      @RequestParam String login,
                      @RequestParam String password)
            throws IOException {
        HttpSession session = req.getSession();
        if (this.userService.isCredential(login, password)) {
            User user = this.userService.findByLogin(login);
            session.setAttribute("id", user.getId());
            session.setAttribute("login", user.getLogin());
            logger.debug("Found user: {}", user.toString());
            resp.sendRedirect("/");
        } else {
            resp.sendRedirect("/login");
        }
    }

    /**
     * Method for registration new user in application.
     *
     * @param req      HttpServletRequest
     * @param resp     HttpServletResponse
     * @param login    user login
     * @param password user password
     * @param confirm  confirmed password.
     * @throws IOException
     */
    @PostMapping(value = "/api/reg")
    public void regNewUser(HttpServletRequest req,
                           HttpServletResponse resp,
                           @RequestParam String login,
                           @RequestParam String password,
                           @RequestParam String confirm)
            throws IOException {
        HttpSession session = req.getSession();
        if (password.equals(confirm)) {
            User user = new User(login, password);
            logger.debug("New user: {}", user.toString());
            if (!this.userService.isExist(user)) {
                this.userService.add(user);
                session.setAttribute("id", user.getId());
                session.setAttribute("login", login);
                resp.sendRedirect("/");
            } else {
                resp.sendRedirect("/registration");
            }
        } else {
            logger.error("Password does not match!");
        }
    }
}

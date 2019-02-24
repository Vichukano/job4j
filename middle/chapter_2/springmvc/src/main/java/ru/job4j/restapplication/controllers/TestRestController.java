package ru.job4j.restapplication.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.restapplication.models.User;
import ru.job4j.restapplication.persistence.MemoryStore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
public class TestRestController {
    private MemoryStore store;

    @Autowired
    public TestRestController(MemoryStore store) {
        this.store = store;
    }

    @GetMapping(value = "/api")
    public User start() {
        return new User("старт", 0);
    }

    @GetMapping(value = "/api/all")
    public List<User> getAll() {
        return this.store.findAll();
    }

    /**
     * Trying to get data from html form.
     * @param req
     */
    @PostMapping(value = "/api/add")
    public void addUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("name");
        Integer age = Integer.parseInt(req.getParameter("age"));
        User user = new User(name, age);
        this.store.add(user);
        resp.sendRedirect("/");
    }
}

package ru.job4j.restapplication.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/all")
    public String all() {
        return "all";
    }

}

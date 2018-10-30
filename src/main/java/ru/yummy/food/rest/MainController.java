package ru.yummy.food.rest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class MainController {


    @PostMapping("/parsePage")
    public String parsePagee() {

        return "OK";
    }

    @PostConstruct
    private void initValues(){}


}

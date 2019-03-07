package ru.yummy.eat.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import ru.yummy.eat.service.ParseService;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class MainController {


    @Autowired
    @Qualifier("parseServiceImpl")
    ParseService parseService;

    @GetMapping("/testAllPages")
    public String testAllPage() {
        parseService.scheduledTestParseMenu();
        return "OK";
    }

    @PostConstruct
    private void initValues(){}


}

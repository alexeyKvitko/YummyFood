package ru.yummy.eat.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yummy.eat.service.ParseService;

import javax.annotation.PostConstruct;

@Component
public class ScheduledTasks {

    @Autowired
    ParseService parseService;


//    @Scheduled(initialDelay = 2000, fixedRate = 10 * 60 * 1000)
//    public void fillFlatEstates() {

    @PostConstruct
    public void testScheduling(){
        parseService.scheduledParseMenu();
    }


}

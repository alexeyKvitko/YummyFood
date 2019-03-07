package ru.yummy.eat.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.yummy.eat.service.ParseService;

@Component
public class ScheduledTasks {

    @Autowired
    ParseService parseService;


    @Scheduled(cron = "${scheduling.job.cron}")
    public void parsingCompanies(){
        parseService.scheduledTestParseMenu();
    }

}

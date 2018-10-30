package ru.yummy.food;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class YammyFoodApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources( YammyFoodApplication.class );
    }

    public static void main(String[] args) {
        SpringApplication.run( YammyFoodApplication.class, args);
    }
}

//@SpringBootApplication
//public class AurresWSApplication{
//
//    public static void main(String[] args) {
//        SpringApplication.run(AurresWSApplication.class, args);
//    }
//
//}

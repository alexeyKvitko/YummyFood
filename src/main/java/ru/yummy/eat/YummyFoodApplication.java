package ru.yummy.eat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class YummyFoodApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources( YummyFoodApplication.class );
    }

    public static void main(String[] args) {
        SpringApplication.run( YummyFoodApplication.class, args);
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

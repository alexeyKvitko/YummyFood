package ru.yummy.eat.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class AppProperties {

        private static final Logger LOG = LoggerFactory.getLogger(AppProperties.class);

        private static AppProperties properties = new AppProperties();

        private static List<String> menuCategoryPizza;
        private static List<String> menuCategorySushi;
        private static List<String> menuCategoryBurger;
        private static List<String> menuCategoryGrill;
        private static List<String> menuCategoryWOK;

        private static boolean initialized = false;

        public static AppProperties getProperties(){
            if ( !initialized ){
                initProperties();
            }
            return properties;
        }

        private static void initProperties(){
            Properties props = null;
            try {
                props = PropertiesLoaderUtils.loadAllProperties("application.properties");
            } catch (IOException e) {
                LOG.error("Can not load properties file. Exit ...");
                System.exit(0);
            }

            menuCategoryPizza = Arrays.asList( ((String) props.get("menu.category.pizza")).split(",") );
            menuCategorySushi = Arrays.asList( ((String) props.get("menu.category.sushi")).split(",") );
            menuCategoryBurger = Arrays.asList( ((String) props.get("menu.category.burger")).split(",") );
            menuCategoryGrill = Arrays.asList( ((String) props.get("menu.category.grill")).split(",") );
            menuCategoryWOK = Arrays.asList( ((String) props.get("menu.category.wok")).split(",") );
        }

    public static List<String> getMenuCategoryPizza() {
        return menuCategoryPizza;
    }

    public static List<String> getMenuCategorySushi() {
        return menuCategorySushi;
    }

    public static List<String> getMenuCategoryBurger() {
        return menuCategoryBurger;
    }

    public static List<String> getMenuCategoryGrill() {
        return menuCategoryGrill;
    }

    public static List<String> getMenuCategoryWOK() {
        return menuCategoryWOK;
    }
}

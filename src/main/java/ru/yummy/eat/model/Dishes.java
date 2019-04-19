package ru.yummy.eat.model;

import java.io.Serializable;
import java.util.List;

public class Dishes implements Serializable {

    List<MenuEntityModel> dishes;

    public List< MenuEntityModel > getDishes() {
        return dishes;
    }

    public void setDishes( List< MenuEntityModel > dishes ) {
        this.dishes = dishes;
    }
}

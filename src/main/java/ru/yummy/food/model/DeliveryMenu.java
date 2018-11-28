package ru.yummy.food.model;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import java.io.Serializable;
import java.util.List;

@AutoProperty
public class DeliveryMenu implements Serializable {

    List<MenuTypeModel> menuTypes;
    List<MenuCategoryModel> menuCategories;


    public List<MenuTypeModel> getMenuTypes() {
        return menuTypes;
    }

    public void setMenuTypes(List<MenuTypeModel> menuTypes) {
        this.menuTypes = menuTypes;
    }

    public List<MenuCategoryModel> getMenuCategories() {
        return menuCategories;
    }

    public void setMenuCategories(List<MenuCategoryModel> menuCategories) {
        this.menuCategories = menuCategories;
    }

    @Override public boolean equals(Object o) {
        return Pojomatic.equals(this, o);
    }

    @Override public int hashCode() {
        return Pojomatic.hashCode(this);
    }

    @Override public String toString() {
        return Pojomatic.toString(this);
    }
}

package ru.yummy.food.model;

import org.pojomatic.annotations.AutoProperty;

import java.io.Serializable;
import java.util.List;

@AutoProperty
public class MenuTypeModel extends DictionaryModel implements Serializable {

    private List<MenuCategoryModel> menuCategoryModels;

    public List<MenuCategoryModel> getMenuCategoryModels() {
        return menuCategoryModels;
    }

    public void setMenuCategoryModels(List<MenuCategoryModel> menuCategoryModels) {
        this.menuCategoryModels = menuCategoryModels;
    }
}


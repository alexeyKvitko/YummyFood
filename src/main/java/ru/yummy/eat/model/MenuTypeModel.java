package ru.yummy.eat.model;

import org.pojomatic.annotations.AutoProperty;

import java.util.Comparator;
import java.util.List;

@AutoProperty
public class MenuTypeModel extends DictionaryModel  {

    private List<MenuCategoryModel> menuCategories;

    public List<MenuCategoryModel> getMenuCategories() {
        return menuCategories;
    }

    public void setMenuCategories(List<MenuCategoryModel> menuCategories) {
        this.menuCategories = menuCategories;
    }

}


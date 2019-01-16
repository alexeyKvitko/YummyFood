package ru.yummy.eat.model;

import org.pojomatic.annotations.AutoProperty;

import java.io.Serializable;

@AutoProperty
public class MenuCategoryModel extends DictionaryModel implements Serializable {

    Integer menuTypeId;

    public Integer getMenuTypeId() {
        return menuTypeId;
    }

    public void setMenuTypeId(Integer menuTypeId) {
        this.menuTypeId = menuTypeId;
    }
}

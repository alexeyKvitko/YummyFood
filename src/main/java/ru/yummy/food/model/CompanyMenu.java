package ru.yummy.food.model;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import java.io.Serializable;
import java.util.List;

@AutoProperty
public class CompanyMenu implements Serializable {

    private ParseMenuModel parseMenu;
    private List<MenuEntityModel> menuEntities;

    public ParseMenuModel getParseMenu() {
        return parseMenu;
    }

    public void setParseMenu(ParseMenuModel parseMenu) {
        this.parseMenu = parseMenu;
    }

    public List<MenuEntityModel> getMenuEntities() {
        return menuEntities;
    }

    public void setMenuEntities(List<MenuEntityModel> menuEntities) {
        this.menuEntities = menuEntities;
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

package ru.yummy.eat.model;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;
import ru.yummy.eat.entity.MenuOrder;

import java.util.List;

@AutoProperty
public class CompanyEdit extends CompanyInfo {

    private List<MenuTypeModel> deliveryMenuTypes;
    private List<MenuCategoryModel> deliveryMenuCategories;
    private List<DictionaryModel> cities;
    private List<MenuOrder> menuOrders;

    public List<MenuTypeModel> getDeliveryMenuTypes() {
        return deliveryMenuTypes;
    }

    public void setDeliveryMenuTypes(List<MenuTypeModel> deliveryMenuTypes) {
        this.deliveryMenuTypes = deliveryMenuTypes;
    }

    public List<MenuCategoryModel> getDeliveryMenuCategories() {
        return deliveryMenuCategories;
    }

    public void setDeliveryMenuCategories(List<MenuCategoryModel> deliveryMenuCategories) {
        this.deliveryMenuCategories = deliveryMenuCategories;
    }

    public List<DictionaryModel> getCities() {
        return cities;
    }

    public void setCities(List<DictionaryModel> cities) {
        this.cities = cities;
    }

    public List<MenuOrder> getMenuOrders() {
        return menuOrders;
    }

    public void setMenuOrders(List<MenuOrder> menuOrders) {
        this.menuOrders = menuOrders;
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

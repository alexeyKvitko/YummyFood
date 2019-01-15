package ru.yummy.food.model;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;
import ru.yummy.food.entity.City;

import java.io.Serializable;
import java.util.List;

@AutoProperty
public class BootstrapModel implements Serializable {

    private List<CompanyModel> companies;
    private List<DictionaryModel> cities;
    private DeliveryMenu deliveryMenu;
    private String deliveryCity;
    private boolean isDefault;

    public List<CompanyModel> getCompanies() {
        return companies;
    }

    public void setCompanies(List<CompanyModel> companies) {
        this.companies = companies;
    }

    public List<DictionaryModel> getCities() {
        return cities;
    }

    public void setCities(List<DictionaryModel> cities) {
        this.cities = cities;
    }

    public DeliveryMenu getDeliveryMenu() {
        return deliveryMenu;
    }

    public void setDeliveryMenu(DeliveryMenu deliveryMenu) {
        this.deliveryMenu = deliveryMenu;
    }

    public String getDeliveryCity() {
        return deliveryCity;
    }

    public void setDeliveryCity(String deliveryCity) {
        this.deliveryCity = deliveryCity;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
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

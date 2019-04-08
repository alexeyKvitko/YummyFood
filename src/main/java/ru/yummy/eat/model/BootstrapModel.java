package ru.yummy.eat.model;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;
import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;
import java.util.List;

@AutoProperty
public class BootstrapModel implements Serializable {

    private List<CompanyModel> companies;
    private List<DictionaryModel> cities;
    private List<CompanyActionModel> companyActions;
    private DeliveryMenu deliveryMenu;
    private FastMenu fastMenu;
    private String deliveryCity;
    private String staticUrl;
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

    public List<CompanyActionModel> getCompanyActions() {
        return companyActions;
    }

    public void setCompanyActions(List<CompanyActionModel> companyActions) {
        this.companyActions = companyActions;
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

    public FastMenu getFastMenu() {
        return fastMenu;
    }

    public void setFastMenu(FastMenu fastMenu) {
        this.fastMenu = fastMenu;
    }

    public String getStaticUrl() {
        return staticUrl;
    }

    public void setStaticUrl(String staticUrl) {
        this.staticUrl = staticUrl;
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

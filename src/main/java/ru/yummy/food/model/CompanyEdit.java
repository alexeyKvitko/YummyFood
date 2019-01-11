package ru.yummy.food.model;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;
import ru.yummy.food.entity.CompanyShort;

import java.util.List;

@AutoProperty
public class CompanyEdit extends CompanyInfo {

    private List<MenuTypeModel> deliveryMenuTypes;
    private List<MenuCategoryModel> deliveryMenuCategories;
    private List<DictionaryModel> cities;
    private CompanyShort companyShort;

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

    public CompanyShort getCompanyShort() {
        return companyShort;
    }

    public void setCompanyShort(CompanyShort companyShort) {
        this.companyShort = companyShort;
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

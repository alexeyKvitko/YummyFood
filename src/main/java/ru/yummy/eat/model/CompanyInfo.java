package ru.yummy.eat.model;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import java.io.Serializable;
import java.util.List;

@AutoProperty
public class CompanyInfo implements Serializable {

    protected CompanyModel companyModel;
    protected List<MenuTypeModel> menuTypes;
    protected List<MenuEntityModel> menuEntities;

    public CompanyModel getCompanyModel() {
        return companyModel;
    }

    public void setCompanyModel(CompanyModel companyModel) {
        this.companyModel = companyModel;
    }

    public List<MenuTypeModel> getMenuTypes() {
        return menuTypes;
    }

    public void setMenuTypes(List<MenuTypeModel> menuTypes) {
        this.menuTypes = menuTypes;
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

package ru.yummy.food.model;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;
import ru.yummy.food.AppConstants;
import ru.yummy.food.entity.City;
import java.io.Serializable;

@AutoProperty
public class CompanyModel implements Serializable {


    private Integer id;
    private String companyName;
    private String displayName;
    private City city;
    private String url;
    private String email;
    private String phoneOne;
    private String phoneTwo;
    private String phoneThree;
    private String logo;

    public CompanyModel() {
        this.id = AppConstants.FAKE_ID;
        this.companyName = "";
        this.displayName = "";
        this.city = new City();
        this.url = "";
        this.email = "";
        this.phoneOne = "";
        this.phoneTwo = "";
        this.phoneThree = "";
        this.logo = AppConstants.DEFAULT_LOGO;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneOne() {
        return phoneOne;
    }

    public void setPhoneOne(String phoneOne) {
        this.phoneOne = phoneOne;
    }

    public String getPhoneTwo() {
        return phoneTwo;
    }

    public void setPhoneTwo(String phoneTwo) {
        this.phoneTwo = phoneTwo;
    }

    public String getPhoneThree() {
        return phoneThree;
    }

    public void setPhoneThree(String phoneThree) {
        this.phoneThree = phoneThree;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
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



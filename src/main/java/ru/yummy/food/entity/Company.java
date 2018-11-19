package ru.yummy.food.entity;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "companies")
@AutoProperty
public class Company implements Serializable {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Integer id;

    @Column( name = "company_name" )
    private String companyName;

    @Column( name = "display_name" )
    private String displayName;

    @Column( name = "city_id" )
    private Integer cityId;

    @Column( name = "url" )
    private String url;

    @Column( name = "email" )
    private String email;

    @Column( name = "phone_one" )
    private String phoneOne;

    @Column( name = "phone_two" )
    private String phoneTwo;

    @Column( name = "phone_three" )
    private String phoneThree;

    @Column( name = "logo" )
    private String logo;

    public Integer getId() {
        return id;
    }

    public void setId( Integer id ) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName( String companyName ) {
        this.companyName = companyName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName( String displayName ) {
        this.displayName = displayName;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId( Integer cityId ) {
        this.cityId = cityId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl( String url ) {
        this.url = url;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail( String email ) {
        this.email = email;
    }

    public String getPhoneOne() {
        return phoneOne;
    }

    public void setPhoneOne( String phoneOne ) {
        this.phoneOne = phoneOne;
    }

    public String getPhoneTwo() {
        return phoneTwo;
    }

    public void setPhoneTwo( String phoneTwo ) {
        this.phoneTwo = phoneTwo;
    }

    public String getPhoneThree() {
        return phoneThree;
    }

    public void setPhoneThree( String phoneThree ) {
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

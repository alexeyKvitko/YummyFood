package ru.yummy.eat.entity;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import javax.persistence.*;

@Entity
@Table(name = "company_action")
@AutoProperty
public class CompanyAction {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(name="company_id")
    private Integer companyId;

    @Column(name="company_name")
    private String companyName;

    @Column(name="action_img_url")
    private String actionImgUrl;

    @Column(name="full_screen_action")
    private String fullScreenAction;

    @Column(name="city_id")
    private Integer cityId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getFullScreenAction() {
        return fullScreenAction;
    }

    public void setFullScreenAction(String fullScreenAction) {
        this.fullScreenAction = fullScreenAction;
    }

    public String getActionImgUrl() {
        return actionImgUrl;
    }

    public void setActionImgUrl(String actionImgUrl) {
        this.actionImgUrl = actionImgUrl;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
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

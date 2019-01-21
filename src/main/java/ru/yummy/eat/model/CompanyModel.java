package ru.yummy.eat.model;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;
import ru.yummy.eat.AppConstants;
import ru.yummy.eat.entity.City;

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
    private String delivery;
    private String commentCount;
    private String deliveryCondition;
    private Integer payTypeCash;
    private Integer payTypeCard;
    private Integer payTypeWallet;
    private Integer weekdayStart;
    private Integer weekdayEnd;
    private Integer dayoffStart;
    private Integer dayoffEnd;
    private Integer foodPoint;
    private Integer action;
    private String weekdayWork;
    private String dayoffWork;

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

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    public String getDeliveryCondition() {
        return deliveryCondition;
    }

    public void setDeliveryCondition(String deliveryCondition) {
        this.deliveryCondition = deliveryCondition;
    }

    public Integer getPayTypeCash() {
        return payTypeCash;
    }

    public void setPayTypeCash(Integer payTypeCash) {
        this.payTypeCash = payTypeCash;
    }

    public Integer getPayTypeCard() {
        return payTypeCard;
    }

    public void setPayTypeCard(Integer payTypeCard) {
        this.payTypeCard = payTypeCard;
    }

    public Integer getPayTypeWallet() {
        return payTypeWallet;
    }

    public void setPayTypeWallet(Integer payTypeWallet) {
        this.payTypeWallet = payTypeWallet;
    }

    public Integer getWeekdayStart() {
        return weekdayStart;
    }

    public void setWeekdayStart(Integer weekdayStart) {
        this.weekdayStart = weekdayStart;
    }

    public Integer getWeekdayEnd() {
        return weekdayEnd;
    }

    public void setWeekdayEnd(Integer weekdayEnd) {
        this.weekdayEnd = weekdayEnd;
    }

    public Integer getDayoffStart() {
        return dayoffStart;
    }

    public void setDayoffStart(Integer dayoffStart) {
        this.dayoffStart = dayoffStart;
    }

    public Integer getDayoffEnd() {
        return dayoffEnd;
    }

    public void setDayoffEnd(Integer dayoffEnd) {
        this.dayoffEnd = dayoffEnd;
    }

    public Integer getFoodPoint() {
        return foodPoint;
    }

    public void setFoodPoint(Integer foodPoint) {
        this.foodPoint = foodPoint;
    }

    public Integer getAction() {
        return action;
    }

    public void setAction(Integer action) {
        this.action = action;
    }

    public String getWeekdayWork() {
        return weekdayWork;
    }

    public void setWeekdayWork(String weekdayWork) {
        this.weekdayWork = weekdayWork;
    }

    public String getDayoffWork() {
        return dayoffWork;
    }

    public void setDayoffWork(String dayoffWork) {
        this.dayoffWork = dayoffWork;
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


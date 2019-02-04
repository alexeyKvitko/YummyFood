package ru.yummy.eat.entity;

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

    @Column(name="delivery")
    private Integer delivery;

    @Column(name="comment_count")
    private String commentCount;

    @Column(name="delivery_condition")
    private String deliveryCondition;

    @Column(name="pay_type_cash")
    private Integer payTypeCash;

    @Column(name="pay_type_card")
    private Integer payTypeCard;

    @Column(name="pay_type_wallet")
    private Integer payTypeWallet;

    @Column(name="weekday_start")
    private Integer weekdayStart;

    @Column(name="weekday_end")
    private Integer weekdayEnd;

    @Column(name="dayoff_start")
    private Integer dayoffStart;

    @Column(name="dayoff_end")
    private Integer dayoffEnd;

    @Column(name="food_point")
    private Integer foodPoint;

    @Column(name="action")
    private Integer action;

    @Column(name="weekday_str")
    private String weekdayWork;

    @Column(name="dayoff_str")
    private String dayoffWork;

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

    public Integer getDelivery() {
        return delivery;
    }

    public void setDelivery(Integer delivery) {
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

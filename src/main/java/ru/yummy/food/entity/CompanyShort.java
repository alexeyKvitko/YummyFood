package ru.yummy.food.entity;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import javax.persistence.*;

@Entity
@Table(name = "company_info")
@AutoProperty
public class CompanyShort{
    
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Integer id;

    @Column(name="city_id")
    private Integer cityId;
    
    @Column(name="company_id")
    private Integer companyId;

    @Column(name="company_name")
    private String companyName;

    @Column(name="company_logo")
    private String companyLogo;

    @Column(name="delivery")
    private String delivery;

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

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
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

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
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

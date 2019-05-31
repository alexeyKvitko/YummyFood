package ru.yummy.eat.entity;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import javax.persistence.*;

@Entity
@Table(name = "client_order")
@AutoProperty
public class ClientOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "client_uuid")
    private String clientUuid;

    @Column(name = "company_one_id")
    private Integer companyOneId;

    @Column(name = "company_one_name")
    private String companyOneName;

    @Column(name = "company_two_id")
    private Integer companyTwoId;

    @Column(name = "company_two_name")
    private String companyTwoName;

    @Column(name = "company_logo")
    private String companyLogo;

    @Column(name = "order_date")
    private String orderDate;

    @Column(name = "order_time")
    private String orderTime;

    @Column(name = "order_price")
    private Integer orderPrice;

    @Column(name = "order_status")
    private String orderStatus;

    @Column(name = "nick_name")
    private String nickName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "city")
    private String city;

    @Column(name = "street")
    private String street;

    @Column(name = "building")
    private String building;

    @Column(name = "flat")
    private String flat;

    @Column(name = "floor")
    private String floor;

    @Column(name = "entry")
    private String entry;

    @Column(name = "intercom")
    private String intercom;

    @Column(name = "need_change")
    private Integer needChange;

    @Column(name = "comment")
    private String comment;

    @Column(name = "pay_type")
    private String payType;

    @Column(name = "pay_status")
    private String payStatus;

    @Column(name = "pay_amount")
    private String payAmount;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClientUuid() {
        return clientUuid;
    }

    public void setClientUuid(String clientUuid) {
        this.clientUuid = clientUuid;
    }

    public Integer getCompanyOneId() {
        return companyOneId;
    }

    public void setCompanyOneId(Integer companyOneId) {
        this.companyOneId = companyOneId;
    }

    public String getCompanyOneName() {
        return companyOneName;
    }

    public void setCompanyOneName(String companyOneName) {
        this.companyOneName = companyOneName;
    }

    public Integer getCompanyTwoId() {
        return companyTwoId;
    }

    public void setCompanyTwoId(Integer companyTwoId) {
        this.companyTwoId = companyTwoId;
    }

    public String getCompanyTwoName() {
        return companyTwoName;
    }

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }

    public void setCompanyTwoName(String companyTwoName) {
        this.companyTwoName = companyTwoName;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public Integer getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(Integer orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    public String getIntercom() {
        return intercom;
    }

    public void setIntercom(String intercom) {
        this.intercom = intercom;
    }

    public Integer getNeedChange() {
        return needChange;
    }

    public void setNeedChange(Integer needChange) {
        this.needChange = needChange;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(String payAmount) {
        this.payAmount = payAmount;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    @Override
    public boolean equals(Object o) {
        return Pojomatic.equals(this, o);
    }

    @Override
    public int hashCode() {
        return Pojomatic.hashCode(this);
    }

    @Override
    public String toString() {
        return Pojomatic.toString(this);
    }
}

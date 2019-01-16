package ru.yummy.eat.model;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;
import java.io.Serializable;

@AutoProperty
public class MenuEntityModel implements Serializable {

    private Integer id;
    private Integer companyId;
    private Integer typeId;
    private Integer categoryId;
    private String name;
    private String displayName;
    private String description;
    private String imageUrl;
    private String weightOne;
    private String sizeOne;
    private Integer priceOne;
    private String weightTwo;
    private String sizeTwo;
    private Integer priceTwo;
    private String weightThree;
    private String sizeThree;
    private Integer priceThree;
    private String weightFour;
    private String sizeFour;
    private Integer priceFour;
    private String status;

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

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getWeightOne() {
        return weightOne;
    }

    public void setWeightOne(String weightOne) {
        this.weightOne = weightOne;
    }

    public String getSizeOne() {
        return sizeOne;
    }

    public void setSizeOne(String sizeOne) {
        this.sizeOne = sizeOne;
    }

    public Integer getPriceOne() {
        return priceOne;
    }

    public void setPriceOne(Integer priceOne) {
        this.priceOne = priceOne;
    }

    public String getWeightTwo() {
        return weightTwo;
    }

    public void setWeightTwo(String weightTwo) {
        this.weightTwo = weightTwo;
    }

    public String getSizeTwo() {
        return sizeTwo;
    }

    public void setSizeTwo(String sizeTwo) {
        this.sizeTwo = sizeTwo;
    }

    public Integer getPriceTwo() {
        return priceTwo;
    }

    public void setPriceTwo(Integer priceTwo) {
        this.priceTwo = priceTwo;
    }

    public String getWeightThree() {
        return weightThree;
    }

    public void setWeightThree(String weightThree) {
        this.weightThree = weightThree;
    }

    public String getSizeThree() {
        return sizeThree;
    }

    public void setSizeThree(String sizeThree) {
        this.sizeThree = sizeThree;
    }

    public Integer getPriceThree() {
        return priceThree;
    }

    public void setPriceThree(Integer priceThree) {
        this.priceThree = priceThree;
    }

    public String getWeightFour() {
        return weightFour;
    }

    public void setWeightFour(String weightFour) {
        this.weightFour = weightFour;
    }

    public String getSizeFour() {
        return sizeFour;
    }

    public void setSizeFour(String sizeFour) {
        this.sizeFour = sizeFour;
    }

    public Integer getPriceFour() {
        return priceFour;
    }

    public void setPriceFour(Integer priceFour) {
        this.priceFour = priceFour;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

package ru.yummy.eat.entity;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import javax.persistence.*;

@Entity
@Table(name = "menu_entity")
@AutoProperty
public class MenuEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(name="name")
    private String name;

    @Column(name="display_name")
    private String displayName;

    @Column(name="description")
    private String description;

    @Column(name="image_url")
    private String imageUrl;

    @Column(name="weight_one")
    private String weightOne;

    @Column(name="size_one")
    private String sizeOne;

    @Column(name="price_one")
    private Integer priceOne;

    @Column(name="weight_two")
    private String weightTwo;

    @Column(name="size_two")
    private String sizeTwo;

    @Column(name="price_two")
    private Integer priceTwo;

    @Column(name="weight_three")
    private String weightThree;

    @Column(name="size_three")
    private String sizeThree;

    @Column(name="price_three")
    private Integer priceThree;

    @Column(name="weight_four")
    private String weightFour;

    @Column(name="size_four")
    private String sizeFour;

    @Column(name="price_four")
    private Integer priceFour;

    @Column(name="status")
    private String status;

    public Integer getId() {
        return id;
    }

    public void setId( Integer id ) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName( String displayName ) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription( String description ) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl( String imageUrl ) {
        this.imageUrl = imageUrl;
    }

    public String getWeightOne() {
        return weightOne;
    }

    public void setWeightOne( String weightOne ) {
        this.weightOne = weightOne;
    }

    public String getSizeOne() {
        return sizeOne;
    }

    public void setSizeOne( String sizeOne ) {
        this.sizeOne = sizeOne;
    }

    public Integer getPriceOne() {
        return priceOne;
    }

    public void setPriceOne( Integer priceOne ) {
        this.priceOne = priceOne;
    }

    public String getWeightTwo() {
        return weightTwo;
    }

    public void setWeightTwo( String weightTwo ) {
        this.weightTwo = weightTwo;
    }

    public String getSizeTwo() {
        return sizeTwo;
    }

    public void setSizeTwo( String sizeTwo ) {
        this.sizeTwo = sizeTwo;
    }

    public Integer getPriceTwo() {
        return priceTwo;
    }

    public void setPriceTwo( Integer priceTwo ) {
        this.priceTwo = priceTwo;
    }

    public String getWeightThree() {
        return weightThree;
    }

    public void setWeightThree( String weightThree ) {
        this.weightThree = weightThree;
    }

    public String getSizeThree() {
        return sizeThree;
    }

    public void setSizeThree( String sizeThree ) {
        this.sizeThree = sizeThree;
    }

    public Integer getPriceThree() {
        return priceThree;
    }

    public void setPriceThree( Integer priceThree ) {
        this.priceThree = priceThree;
    }

    public String getWeightFour() {
        return weightFour;
    }

    public void setWeightFour( String weightFour ) {
        this.weightFour = weightFour;
    }

    public String getSizeFour() {
        return sizeFour;
    }

    public void setSizeFour( String sizeFour ) {
        this.sizeFour = sizeFour;
    }

    public Integer getPriceFour() {
        return priceFour;
    }

    public void setPriceFour( Integer priceFour ) {
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

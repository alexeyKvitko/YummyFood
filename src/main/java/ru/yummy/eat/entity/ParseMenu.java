package ru.yummy.eat.entity;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table( name = "parse_menu" )
@AutoProperty
public class ParseMenu implements Serializable {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Integer id;

    @Column( name = "company_id" )
    private Integer companyId;

    @Column( name = "type_id" )
    private Integer typeId;

    @Column( name = "category_id" )
    private Integer categoryId;

    @Column( name = "parse_url" )
    private String parseUrl;

    @Column( name = "prefix_url" )
    private String prefixUrl;

    @Column( name = "tag_trash" )
    private String tagTrash;

    @Column( name = "tag_end_section" )
    private String tagEndSection;

    @Column( name = "tag_name" )
    private String tagName;

    @Column( name = "tag_description" )
    private String tagDescription;

    @Column( name = "tag_image_url" )
    private String tagImageUrl;

    @Column( name = "tag_wo" )
    private String tagWeightOne;

    @Column( name = "tag_so" )
    private String tagSizeOne;

    @Column( name = "tag_po" )
    private String tagPriceOne;

    @Column( name = "tag_wt" )
    private String tagWeightTwo;

    @Column( name = "tag_st" )
    private String tagSizeTwo;

    @Column( name = "tag_pt" )
    private String tagPriceTwo;

    @Column( name = "tag_wth" )
    private String tagWeightThree;

    @Column( name = "tag_sth" )
    private String tagSizeThree;

    @Column( name = "tag_pth" )
    private String tagPriceThree;

    @Column( name = "tag_wf" )
    private String tagWeightFour;

    @Column( name = "tag_sf" )
    private String tagSizeFour;

    @Column( name = "tag_pf" )
    private String tagPriceFour;

    @Column( name = "processed" )
    private Integer processed;

    public Integer getId() {
        return id;
    }

    public void setId( Integer id ) {
        this.id = id;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId( Integer companyId ) {
        this.companyId = companyId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId( Integer typeId ) {
        this.typeId = typeId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId( Integer categoryId ) {
        this.categoryId = categoryId;
    }

    public String getParseUrl() {
        return parseUrl;
    }

    public void setParseUrl( String parseUrl ) {
        this.parseUrl = parseUrl;
    }

    public String getPrefixUrl() {
        return prefixUrl;
    }

    public void setPrefixUrl(String prefixUrl) {
        this.prefixUrl = prefixUrl;
    }

    public String getTagEndSection() {
        return tagEndSection;
    }

    public void setTagEndSection(String tagEndSection) {
        this.tagEndSection = tagEndSection;
    }

    public String getTagTrash() {
        return tagTrash;
    }

    public void setTagTrash( String tagTrash ) {
        this.tagTrash = tagTrash;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName( String tagName ) {
        this.tagName = tagName;
    }

    public String getTagDescription() {
        return tagDescription;
    }

    public void setTagDescription( String tagDescription ) {
        this.tagDescription = tagDescription;
    }

    public String getTagImageUrl() {
        return tagImageUrl;
    }

    public void setTagImageUrl( String tagImageUrl ) {
        this.tagImageUrl = tagImageUrl;
    }

    public String getTagWeightOne() {
        return tagWeightOne;
    }

    public void setTagWeightOne( String tagWeightOne ) {
        this.tagWeightOne = tagWeightOne;
    }

    public String getTagSizeOne() {
        return tagSizeOne;
    }

    public void setTagSizeOne( String tagSizeOne ) {
        this.tagSizeOne = tagSizeOne;
    }

    public String getTagPriceOne() {
        return tagPriceOne;
    }

    public void setTagPriceOne( String tagPriceOne ) {
        this.tagPriceOne = tagPriceOne;
    }

    public String getTagWeightTwo() {
        return tagWeightTwo;
    }

    public void setTagWeightTwo( String tagWeightTwo ) {
        this.tagWeightTwo = tagWeightTwo;
    }

    public String getTagSizeTwo() {
        return tagSizeTwo;
    }

    public void setTagSizeTwo( String tagSizeTwo ) {
        this.tagSizeTwo = tagSizeTwo;
    }

    public String getTagPriceTwo() {
        return tagPriceTwo;
    }

    public void setTagPriceTwo( String tagPriceTwo ) {
        this.tagPriceTwo = tagPriceTwo;
    }

    public String getTagWeightThree() {
        return tagWeightThree;
    }

    public void setTagWeightThree( String tagWeightThree ) {
        this.tagWeightThree = tagWeightThree;
    }

    public String getTagSizeThree() {
        return tagSizeThree;
    }

    public void setTagSizeThree( String tagSizeThree ) {
        this.tagSizeThree = tagSizeThree;
    }

    public String getTagPriceThree() {
        return tagPriceThree;
    }

    public void setTagPriceThree( String tagPriceThree ) {
        this.tagPriceThree = tagPriceThree;
    }

    public String getTagWeightFour() {
        return tagWeightFour;
    }

    public void setTagWeightFour( String tagWeightFour ) {
        this.tagWeightFour = tagWeightFour;
    }

    public String getTagSizeFour() {
        return tagSizeFour;
    }

    public void setTagSizeFour( String tagSizeFour ) {
        this.tagSizeFour = tagSizeFour;
    }

    public String getTagPriceFour() {
        return tagPriceFour;
    }

    public void setTagPriceFour( String tagPriceFour ) {
        this.tagPriceFour = tagPriceFour;
    }

    public Integer getProcessed() {
        return processed;
    }

    public void setProcessed(Integer processed) {
        this.processed = processed;
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

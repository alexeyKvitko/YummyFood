package ru.yummy.eat.model;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import javax.persistence.Column;
import java.io.Serializable;

@AutoProperty
public class ParseMenuModel implements Serializable {

    private Integer id;
    private Integer companyId;
    private Integer typeId;
    private Integer categoryId;
    private String htmlResponse;
    private String parseUrl;
    private String prefixUrl;
    private String tagTrash;
    private String tagEndSection;
    private String tagName;
    private String tagDescription;
    private String tagImageUrl;
    private String tagWeightOne;
    private String tagSizeOne;
    private String tagPriceOne;
    private String tagWeightTwo;
    private String tagSizeTwo;
    private String tagPriceTwo;
    private String tagWeightThree;
    private String tagSizeThree;
    private String tagPriceThree;
    private String tagWeightFour;
    private String tagSizeFour;
    private String tagPriceFour;
    private boolean broken;
    private ParseResult parseResult;
    private Integer processed;
    private transient String lastUpdate;
    private transient String updateResult;
    private transient String description;

    public ParseMenuModel() {
        this.parseResult = new ParseResult();
    }

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

    public String getHtmlResponse() {
        return htmlResponse;
    }

    public void setHtmlResponse(String htmlResponse) {
        this.htmlResponse = htmlResponse;
    }

    public String getParseUrl() {
        return parseUrl;
    }

    public void setParseUrl(String parseUrl) {
        this.parseUrl = parseUrl;
    }

    public String getPrefixUrl() {
        return prefixUrl;
    }

    public void setPrefixUrl(String prefixUrl) {
        this.prefixUrl = prefixUrl;
    }

    public String getTagTrash() {
        return tagTrash;
    }

    public void setTagTrash(String tagTrash) {
        this.tagTrash = tagTrash;
    }

    public String getTagEndSection() {
        return tagEndSection;
    }

    public void setTagEndSection(String tagEndSection) {
        this.tagEndSection = tagEndSection;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTagDescription() {
        return tagDescription;
    }

    public void setTagDescription(String tagDescription) {
        this.tagDescription = tagDescription;
    }

    public String getTagImageUrl() {
        return tagImageUrl;
    }

    public void setTagImageUrl(String tagImageUrl) {
        this.tagImageUrl = tagImageUrl;
    }

    public String getTagWeightOne() {
        return tagWeightOne;
    }

    public void setTagWeightOne(String tagWeightOne) {
        this.tagWeightOne = tagWeightOne;
    }

    public String getTagSizeOne() {
        return tagSizeOne;
    }

    public void setTagSizeOne(String tagSizeOne) {
        this.tagSizeOne = tagSizeOne;
    }

    public String getTagPriceOne() {
        return tagPriceOne;
    }

    public void setTagPriceOne(String tagPriceOne) {
        this.tagPriceOne = tagPriceOne;
    }

    public String getTagWeightTwo() {
        return tagWeightTwo;
    }

    public void setTagWeightTwo(String tagWeightTwo) {
        this.tagWeightTwo = tagWeightTwo;
    }

    public String getTagSizeTwo() {
        return tagSizeTwo;
    }

    public void setTagSizeTwo(String tagSizeTwo) {
        this.tagSizeTwo = tagSizeTwo;
    }

    public String getTagPriceTwo() {
        return tagPriceTwo;
    }

    public void setTagPriceTwo(String tagPriceTwo) {
        this.tagPriceTwo = tagPriceTwo;
    }

    public String getTagWeightThree() {
        return tagWeightThree;
    }

    public void setTagWeightThree(String tagWeightThree) {
        this.tagWeightThree = tagWeightThree;
    }

    public String getTagSizeThree() {
        return tagSizeThree;
    }

    public void setTagSizeThree(String tagSizeThree) {
        this.tagSizeThree = tagSizeThree;
    }

    public String getTagPriceThree() {
        return tagPriceThree;
    }

    public void setTagPriceThree(String tagPriceThree) {
        this.tagPriceThree = tagPriceThree;
    }

    public String getTagWeightFour() {
        return tagWeightFour;
    }

    public void setTagWeightFour(String tagWeightFour) {
        this.tagWeightFour = tagWeightFour;
    }

    public String getTagSizeFour() {
        return tagSizeFour;
    }

    public void setTagSizeFour(String tagSizeFour) {
        this.tagSizeFour = tagSizeFour;
    }

    public String getTagPriceFour() {
        return tagPriceFour;
    }

    public void setTagPriceFour(String tagPriceFour) {
        this.tagPriceFour = tagPriceFour;
    }

    public Integer getProcessed() {
        return processed;
    }

    public void setProcessed(Integer processed) {
        this.processed = processed;
    }

    public ParseResult getParseResult() {
        return parseResult;
    }

    public void setParseResult(ParseResult parseResult) {
        this.parseResult = parseResult;
    }

    public boolean isBroken() {
        return broken;
    }

    public void setBroken(boolean broken) {
        this.broken = broken;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getUpdateResult() {
        return updateResult;
    }

    public void setUpdateResult(String updateResult) {
        this.updateResult = updateResult;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

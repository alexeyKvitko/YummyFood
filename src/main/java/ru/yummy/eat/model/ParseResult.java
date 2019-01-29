package ru.yummy.eat.model;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;
import ru.yummy.eat.AppConstants;
import ru.yummy.eat.model.enums.TestFields;
import ru.yummy.eat.model.enums.TestStatus;
import java.io.Serializable;

@AutoProperty
public class ParseResult implements Serializable {

    private String message;
    private String stackTrace;
    private String section;
    private String status;
    private int step;
    private ParseField htmlResult;
    private ParseField htmlClean;
    private ParseField htmlTag;
    private ParseField productName;
    private ParseField productDesc;
    private ParseField productImg;
    private ParseField wspOne;
    private ParseField wspTwo;
    private ParseField wspThree;
    private ParseField wspFour;

    public ParseResult() {
        this.status = AppConstants.SUCCESS;
        this.step = AppConstants.FAKE_ID;
        this.htmlResult = new ParseField( TestFields.HTML_RESULT.value(),this.step, TestStatus.NOT_PROCESSED.value() );
        this.htmlClean = new ParseField( TestFields.HTML_CLEAN.value(),this.step, TestStatus.NOT_PROCESSED.value() );
        this.htmlTag = new ParseField( TestFields.HTML_TAG.value(),this.step, TestStatus.NOT_PROCESSED.value() );
        this.productName = new ParseField( TestFields.PRODUCT_NAME.value(),this.step, TestStatus.NOT_PROCESSED.value() );
        this.productDesc = new ParseField( TestFields.PRODUCT_DESC.value(),this.step, TestStatus.NOT_PROCESSED.value() );
        this.productImg = new ParseField( TestFields.PRODUCT_IMG.value(),this.step, TestStatus.NOT_PROCESSED.value() );
        this.wspOne = new ParseField( TestFields.WSP_ONE.value(),this.step, TestStatus.NOT_PROCESSED.value() );
        this.wspTwo = new ParseField( TestFields.WSP_TWO.value(),this.step, TestStatus.NOT_PROCESSED.value() );
        this.wspThree = new ParseField( TestFields.WSP_THREE.value(),this.step, TestStatus.NOT_PROCESSED.value() );
        this.wspFour = new ParseField( TestFields.WSP_FOUR.value(),this.step, TestStatus.NOT_PROCESSED.value() );
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public ParseField getHtmlResult() {
        return htmlResult;
    }

    public void setHtmlResult(ParseField htmlResult) {
        this.htmlResult = htmlResult;
    }

    public ParseField getHtmlClean() {
        return htmlClean;
    }

    public void setHtmlClean(ParseField htmlClean) {
        this.htmlClean = htmlClean;
    }

    public ParseField getHtmlTag() {
        return htmlTag;
    }

    public void setHtmlTag(ParseField htmlTag) {
        this.htmlTag = htmlTag;
    }

    public ParseField getProductName() {
        return productName;
    }

    public void setProductName(ParseField productName) {
        this.productName = productName;
    }

    public ParseField getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(ParseField productDesc) {
        this.productDesc = productDesc;
    }

    public ParseField getProductImg() {
        return productImg;
    }

    public void setProductImg(ParseField productImg) {
        this.productImg = productImg;
    }

    public ParseField getWspOne() {
        return wspOne;
    }

    public void setWspOne(ParseField wspOne) {
        this.wspOne = wspOne;
    }

    public ParseField getWspTwo() {
        return wspTwo;
    }

    public void setWspTwo(ParseField wspTwo) {
        this.wspTwo = wspTwo;
    }

    public ParseField getWspThree() {
        return wspThree;
    }

    public void setWspThree(ParseField wspThree) {
        this.wspThree = wspThree;
    }

    public ParseField getWspFour() {
        return wspFour;
    }

    public void setWspFour(ParseField wspFour) {
        this.wspFour = wspFour;
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

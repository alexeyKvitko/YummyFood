package ru.yummy.eat.model;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import java.io.Serializable;

@AutoProperty
public class CopyParseData implements Serializable {

    private int companyId;
    private int fromMenuTypeId;
    private int fromMenuCategoryId;
    private int toMenuTypeId;
    private int toMenuCategoryId;

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getFromMenuTypeId() {
        return fromMenuTypeId;
    }

    public void setFromMenuTypeId(int fromMenuTypeId) {
        this.fromMenuTypeId = fromMenuTypeId;
    }

    public int getFromMenuCategoryId() {
        return fromMenuCategoryId;
    }

    public void setFromMenuCategoryId(int fromMenuCategoryId) {
        this.fromMenuCategoryId = fromMenuCategoryId;
    }

    public int getToMenuTypeId() {
        return toMenuTypeId;
    }

    public void setToMenuTypeId(int toMenuTypeId) {
        this.toMenuTypeId = toMenuTypeId;
    }

    public int getToMenuCategoryId() {
        return toMenuCategoryId;
    }

    public void setToMenuCategoryId(int toMenuCategoryId) {
        this.toMenuCategoryId = toMenuCategoryId;
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

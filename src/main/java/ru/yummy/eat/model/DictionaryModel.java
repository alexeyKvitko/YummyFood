package ru.yummy.eat.model;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import java.io.Serializable;
import java.util.Comparator;

@AutoProperty
public class DictionaryModel implements Serializable {

    protected Integer id;
    protected String name;
    protected String displayName;
    protected String latitude;
    protected String longitude;
    protected Integer order;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
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

package ru.yummy.eat.qiwi;

import com.qiwi.billpayments.sdk.model.BillStatus;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import java.io.Serializable;
import java.util.Date;

@AutoProperty
public class QiwiPayStatus implements Serializable {

    private BillStatus value;
    private Date changedDateTime;

    public BillStatus getValue() {
        return value;
    }

    public void setValue(BillStatus value) {
        this.value = value;
    }

    public Date getChangedDateTime() {
        return changedDateTime;
    }

    public void setChangedDateTime(Date changedDateTime) {
        this.changedDateTime = changedDateTime;
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

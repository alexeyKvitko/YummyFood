package ru.yummy.eat.qiwi;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import java.io.Serializable;

@AutoProperty
public class QiwiResponse implements Serializable {

    private QiwiBill bill;
    private String version;

    public QiwiBill getBill() {
        return bill;
    }

    public void setBill(QiwiBill bill) {
        this.bill = bill;
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

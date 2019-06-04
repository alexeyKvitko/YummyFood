package ru.yummy.eat.model;

import java.io.Serializable;
import java.util.List;

public class ExistOrders implements Serializable {

    List<ClientOrderModel> existOrders;


    public ExistOrders( List<ClientOrderModel> existOrders ) {
        this.existOrders = existOrders;
    }

    public List< ClientOrderModel > getExistOrders() {
        return existOrders;
    }

    public void setExistOrders( List< ClientOrderModel > existOrders ) {
        this.existOrders = existOrders;
    }
}

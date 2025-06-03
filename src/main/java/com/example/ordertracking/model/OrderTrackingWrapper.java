package com.example.ordertracking.model;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;

import java.util.List;


@XmlRootElement(name = "orderTrackings")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderTrackingWrapper {

    @XmlElement(name = "orderTracking")
    private List<OrderTracking> orderTrackings;

    public List<OrderTracking> getOrderTrackings() {
        return orderTrackings;
    }

    public void setOrderTrackings(List<OrderTracking> orderTrackings) {
        this.orderTrackings = orderTrackings;
    }
}

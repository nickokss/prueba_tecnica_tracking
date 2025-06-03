package com.example.ordertracking.model;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Entity
@XmlRootElement(name = "orderTracking")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderTracking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;

    private Integer trackingStatusId;

    private OffsetDateTime changeStatusDate;
    private LocalDateTime insertionDate;

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getTrackingStatusId() {
        return trackingStatusId;
    }

    public void setTrackingStatusId(Integer trackingStatusId) {
        this.trackingStatusId = trackingStatusId;
    }

    public OffsetDateTime getChangeStatusDate() {
        return changeStatusDate;
    }

    public void setChangeStatusDate(OffsetDateTime changeStatusDate) {
        this.changeStatusDate = changeStatusDate;
    }

    public LocalDateTime getInsertionDate() {
        return insertionDate;
    }

    public void setInsertionDate(LocalDateTime insertionDate) {
        this.insertionDate = insertionDate;
    }
}

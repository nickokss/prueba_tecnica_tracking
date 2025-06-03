package com.example.ordertracking.repository;

import com.example.ordertracking.model.OrderTracking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderTrackingRepository extends JpaRepository<OrderTracking, Long> {
    List<OrderTracking> findByOrderIdOrderByChangeStatusDateAsc(Long orderId);
}
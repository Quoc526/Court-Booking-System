package com.example.booking.repository;

import com.example.booking.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    
    List<Order> findByUserId(Long userId);
    
    List<Order> findByBookingId(Long bookingId);
    
    List<Order> findByUserIdOrderByCreatedTimeDesc(Long userId);
}

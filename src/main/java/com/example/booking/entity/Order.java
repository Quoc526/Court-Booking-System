package com.example.booking.entity;

import com.example.booking.entity.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order extends BaseEntity {
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id")
    private Booking booking;
    
    @Column(nullable = false)
    private LocalDateTime createdTime;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private OrderStatus status = OrderStatus.NEW;
    
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<OrderDetail> orderDetails = new ArrayList<>();
    
    /**
     * Calculate total price = sum of all order detail line totals
     */
    public BigDecimal calculateTotalPrice() {
        return orderDetails.stream()
            .map(OrderDetail::getLineTotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    @PrePersist
    public void prePersist() {
        if (createdTime == null) {
            createdTime = LocalDateTime.now();
        }
    }
    
    public void addOrderDetail(OrderDetail detail) {
        orderDetails.add(detail);
        detail.setOrder(this);
    }
}

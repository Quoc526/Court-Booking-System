package com.example.booking.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "order_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetail extends BaseEntity {
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_item_id", nullable = false)
    private ServiceItem serviceItem;
    
    @Column(nullable = false)
    private Integer quantity;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal lineTotal;
    
    /**
     * Calculate line total before persisting
     */
    @PrePersist
    @PreUpdate
    public void calculateLineTotal() {
        if (serviceItem != null && quantity != null) {
            this.lineTotal = serviceItem.getUnitPrice()
                .multiply(BigDecimal.valueOf(quantity));
        }
    }
}

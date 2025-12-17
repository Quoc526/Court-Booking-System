package com.example.booking.entity;

import com.example.booking.entity.enums.ServiceCategory;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "service_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceItem extends BaseEntity {
    
    @Column(nullable = false, length = 100)
    private String name;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private ServiceCategory category;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;
    
    @Column(columnDefinition = "TEXT")
    private String description;
}

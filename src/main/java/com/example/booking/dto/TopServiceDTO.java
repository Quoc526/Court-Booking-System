package com.example.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopServiceDTO {
    
    private Long serviceId;
    private String serviceName;
    private Long totalQuantity;
    private java.math.BigDecimal totalRevenue;
}

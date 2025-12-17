package com.example.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceItemResponseDTO {
    
    private Long id;
    private String name;
    private String category;
    private BigDecimal unitPrice;
    private String description;
}

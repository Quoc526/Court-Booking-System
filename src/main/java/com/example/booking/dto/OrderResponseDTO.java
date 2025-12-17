package com.example.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO {
    
    private Long id;
    private Long userId;
    private Long bookingId;
    private LocalDateTime createdTime;
    private String status;
    private BigDecimal totalPrice;
    private List<OrderDetailResponseDTO> orderDetails;
}

package com.example.booking.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDTO {
    
    private Long bookingId;
    
    @NotEmpty(message = "Order items cannot be empty")
    @Valid
    private List<OrderItemDTO> items;
}

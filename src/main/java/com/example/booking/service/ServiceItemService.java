package com.example.booking.service;

import com.example.booking.dto.ServiceItemRequestDTO;
import com.example.booking.dto.ServiceItemResponseDTO;

import java.util.List;

public interface ServiceItemService {
    
    List<ServiceItemResponseDTO> getAllServices();
    
    ServiceItemResponseDTO getServiceById(Long id);
    
    ServiceItemResponseDTO createService(ServiceItemRequestDTO request);
    
    ServiceItemResponseDTO updateService(Long id, ServiceItemRequestDTO request);
    
    void deleteService(Long id);
}

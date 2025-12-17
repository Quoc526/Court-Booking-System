package com.example.booking.service.impl;

import com.example.booking.dto.ServiceItemRequestDTO;
import com.example.booking.dto.ServiceItemResponseDTO;
import com.example.booking.entity.ServiceItem;
import com.example.booking.entity.enums.ServiceCategory;
import com.example.booking.exception.ResourceNotFoundException;
import com.example.booking.repository.ServiceItemRepository;
import com.example.booking.service.ServiceItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServiceItemServiceImpl implements ServiceItemService {
    
    private final ServiceItemRepository serviceItemRepository;
    
    @Override
    public List<ServiceItemResponseDTO> getAllServices() {
        return serviceItemRepository.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    @Override
    public ServiceItemResponseDTO getServiceById(Long id) {
        ServiceItem serviceItem = findEntityById(id);
        return convertToDTO(serviceItem);
    }
    
    @Override
    @Transactional
    public ServiceItemResponseDTO createService(ServiceItemRequestDTO request) {
        ServiceItem serviceItem = ServiceItem.builder()
            .name(request.getName())
            .category(ServiceCategory.valueOf(request.getCategory()))
            .unitPrice(request.getUnitPrice())
            .description(request.getDescription())
            .build();
        
        serviceItem = serviceItemRepository.save(serviceItem);
        return convertToDTO(serviceItem);
    }
    
    @Override
    @Transactional
    public ServiceItemResponseDTO updateService(Long id, ServiceItemRequestDTO request) {
        ServiceItem serviceItem = findEntityById(id);
        
        serviceItem.setName(request.getName());
        serviceItem.setCategory(ServiceCategory.valueOf(request.getCategory()));
        serviceItem.setUnitPrice(request.getUnitPrice());
        serviceItem.setDescription(request.getDescription());
        
        serviceItem = serviceItemRepository.save(serviceItem);
        return convertToDTO(serviceItem);
    }
    
    @Override
    @Transactional
    public void deleteService(Long id) {
        ServiceItem serviceItem = findEntityById(id);
        serviceItemRepository.delete(serviceItem);
    }
    
    private ServiceItem findEntityById(Long id) {
        return serviceItemRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("ServiceItem", "id", id));
    }
    
    private ServiceItemResponseDTO convertToDTO(ServiceItem serviceItem) {
        return ServiceItemResponseDTO.builder()
            .id(serviceItem.getId())
            .name(serviceItem.getName())
            .category(serviceItem.getCategory().name())
            .unitPrice(serviceItem.getUnitPrice())
            .description(serviceItem.getDescription())
            .build();
    }
}

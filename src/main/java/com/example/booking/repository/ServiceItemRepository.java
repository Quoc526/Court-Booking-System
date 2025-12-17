package com.example.booking.repository;

import com.example.booking.entity.ServiceItem;
import com.example.booking.entity.enums.ServiceCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceItemRepository extends JpaRepository<ServiceItem, Long> {
    
    List<ServiceItem> findByCategory(ServiceCategory category);
    
    List<ServiceItem> findByNameContainingIgnoreCase(String name);
}

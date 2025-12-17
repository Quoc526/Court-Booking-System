package com.example.booking.repository;

import com.example.booking.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    
    List<OrderDetail> findByOrderId(Long orderId);
    
    @Query("SELECT od FROM OrderDetail od JOIN od.order o JOIN o.booking b " +
           "WHERE b.schedule.date BETWEEN :startDate AND :endDate")
    List<OrderDetail> findByDateRange(
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate
    );
    
    @Query("SELECT od.serviceItem.id, od.serviceItem.name, SUM(od.quantity) as totalQty, SUM(od.lineTotal) as totalRevenue " +
           "FROM OrderDetail od JOIN od.order o JOIN o.booking b " +
           "WHERE b.schedule.date BETWEEN :startDate AND :endDate " +
           "GROUP BY od.serviceItem.id, od.serviceItem.name " +
           "ORDER BY totalRevenue DESC")
    List<Object[]> getTopServicesByRevenue(
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate
    );
}

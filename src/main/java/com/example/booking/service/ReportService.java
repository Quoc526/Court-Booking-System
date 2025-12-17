package com.example.booking.service;

import com.example.booking.dto.RevenueReportDTO;
import com.example.booking.dto.TopServiceDTO;

import java.time.LocalDate;
import java.util.List;

public interface ReportService {
    
    RevenueReportDTO getRevenueReport(LocalDate from, LocalDate to);
    
    List<TopServiceDTO> getTopServices(LocalDate from, LocalDate to);
}

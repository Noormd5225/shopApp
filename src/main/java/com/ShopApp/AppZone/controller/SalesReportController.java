package com.ShopApp.AppZone.controller;


import com.ShopApp.AppZone.model.Sales;
import com.ShopApp.AppZone.repository.SalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/sales-report")
public class SalesReportController {

    @Autowired
    private SalesRepository salesRepository;

    // Get all sales (only required fields)
    @GetMapping("/all")
    public List<SalesDTO> getAllSales() {
        return salesRepository.findAll().stream()
                .map(s -> new SalesDTO(
                        s.getSalesId(),
                        s.getSalesDate(),
                        s.getOriginalTotal(),
                        s.getTotalDiscount(),
                        s.getRoundedTotal()))
                .collect(Collectors.toList());
    }

    // Filter by exact date
    @GetMapping("/date")
    public List<SalesDTO> getSalesByDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return salesRepository.findBySalesDate(date).stream()
                .map(s -> new SalesDTO(
                        s.getSalesId(),
                        s.getSalesDate(),
                        s.getOriginalTotal(),
                        s.getTotalDiscount(),
                        s.getRoundedTotal()))
                .collect(Collectors.toList());
    }

    // Filter by date range
    @GetMapping("/daterange")
    public List<SalesDTO> getSalesByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        return salesRepository.findBySalesDateBetween(start, end).stream()
                .map(s -> new SalesDTO(
                        s.getSalesId(),
                        s.getSalesDate(),
                        s.getOriginalTotal(),
                        s.getTotalDiscount(),
                        s.getRoundedTotal()))
                .collect(Collectors.toList());
    }

    // DTO class to return only required fields
    static class SalesDTO {
        private String salesId;
        private LocalDate salesDate;
        private java.math.BigDecimal originalTotal;
        private java.math.BigDecimal totalDiscount;
        private java.math.BigDecimal roundedTotal;

        public SalesDTO(String salesId, LocalDate salesDate,
                        java.math.BigDecimal originalTotal,
                        java.math.BigDecimal totalDiscount,
                        java.math.BigDecimal roundedTotal) {
            this.salesId = salesId;
            this.salesDate = salesDate;
            this.originalTotal = originalTotal;
            this.totalDiscount = totalDiscount;
            this.roundedTotal = roundedTotal;
        }

        // Getters
        public String getSalesId() { return salesId; }
        public LocalDate getSalesDate() { return salesDate; }
        public java.math.BigDecimal getOriginalTotal() { return originalTotal; }
        public java.math.BigDecimal getTotalDiscount() { return totalDiscount; }
        public java.math.BigDecimal getRoundedTotal() { return roundedTotal; }
    }
}

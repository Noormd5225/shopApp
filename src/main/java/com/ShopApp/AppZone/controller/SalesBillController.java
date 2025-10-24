package com.ShopApp.AppZone.controller;

import com.ShopApp.AppZone.model.Sales;
import com.ShopApp.AppZone.repository.SalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/sales-bill")
@CrossOrigin("*")
public class SalesBillController {

    @Autowired
    private SalesRepository salesRepository;

    // Get all sales
    @GetMapping("/all")
    public List<Sales> getAllSales() {
        return salesRepository.findAll();
    }

    // Filter by single date
    @GetMapping("/by-date")
    public List<Sales> getByDate(@RequestParam String date) {
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
        return salesRepository.findBySalesDate(localDate);
    }

    // Filter by date range
    @GetMapping("/between-dates")
    public List<Sales> getBetweenDates(@RequestParam String start, @RequestParam String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        return salesRepository.findBySalesDateBetween(startDate, endDate);
    }

    // Today sales
    @GetMapping("/today")
    public List<Sales> getTodaySales() {
        return salesRepository.findBySalesDate(LocalDate.now());
    }

    // Yesterday sales
    @GetMapping("/yesterday")
    public List<Sales> getYesterdaySales() {
        return salesRepository.findBySalesDate(LocalDate.now().minusDays(1));
    }

    // Get sale by ID for bill display
    @GetMapping("/{salesId}")
    public ResponseEntity<Sales> getSaleById(@PathVariable String salesId) {
        return salesRepository.findById(salesId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Filter by customer
    @GetMapping("/by-customer/{customerId}")
    public List<Sales> getSalesByCustomer(@PathVariable String customerId) {
        List<Sales> allSales = salesRepository.findAll();
        List<Sales> filtered = new ArrayList<>();
        for (Sales s : allSales) {
            if (s.getCustomerId().equalsIgnoreCase(customerId)) filtered.add(s);
        }
        return filtered;
    }

    // Filter by total range
    @GetMapping("/by-total-range")
    public List<Sales> getSalesByTotalRange(@RequestParam BigDecimal min,
                                            @RequestParam BigDecimal max) {
        List<Sales> allSales = salesRepository.findAll();
        List<Sales> filtered = new ArrayList<>();
        for (Sales s : allSales) {
            if (s.getGrandTotal().compareTo(min) >= 0 && s.getGrandTotal().compareTo(max) <= 0) {
                filtered.add(s);
            }
        }
        return filtered;
    }
}

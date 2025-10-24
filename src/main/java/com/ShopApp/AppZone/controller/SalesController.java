package com.ShopApp.AppZone.controller;



import com.ShopApp.AppZone.model.Sales;

import com.ShopApp.AppZone.model.SalesItemDTO;
import com.ShopApp.AppZone.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/sales")
public class SalesController {

    @Autowired
    private SalesService salesService;

    // Generate unique sales ID
    @GetMapping("/generate-id")
    public String generateSalesId() {
        return salesService.generateSalesId();
    }





    @GetMapping("/{salesId}")
    public ResponseEntity<Sales> getSaleById(@PathVariable String salesId) {
        Sales sale = salesService.getSaleById(salesId);
        if (sale == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(sale);
    }

    // Update Payments
    @PutMapping("/update-payments/{salesId}")
    public ResponseEntity<Sales> updatePayments(
            @PathVariable String salesId,
            @RequestParam BigDecimal cash,
            @RequestParam BigDecimal upi,
            @RequestParam BigDecimal credit) {

        Sales updatedSale = salesService.updatePayments(salesId, cash, upi, credit);
        if (updatedSale == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updatedSale);
    }
    // Insert sale
    @PostMapping("/insert")
    public Sales insertSale(@RequestParam String salesId,
                            @RequestParam String customerId,
                            @RequestParam(required = false) BigDecimal cash,
                            @RequestParam(required = false) BigDecimal upi,
                            @RequestParam(required = false) BigDecimal credit,
                            @RequestBody List<SalesItemDTO> items) {
        return salesService.saveSale(salesId, customerId, items, cash, upi, credit);
    }
}

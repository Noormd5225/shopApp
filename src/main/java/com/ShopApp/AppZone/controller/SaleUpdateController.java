package com.ShopApp.AppZone.controller;


import com.ShopApp.AppZone.model.Sales;
import com.ShopApp.AppZone.model.SalesItem;
import com.ShopApp.AppZone.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/sale-update")
@CrossOrigin(origins = "*")
public class SaleUpdateController {

    @Autowired
    private SalesService salesService;

    // Get sale by salesId with item details
    @GetMapping("/{salesId}")
    public ResponseEntity<SaleResponse> getSale(@PathVariable String salesId) {
        Sales sale = salesService.getSaleById(salesId);
        if (sale == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(new SaleResponse(sale));
    }

    // Update payments
    @PutMapping("/update/{salesId}")
    public ResponseEntity<SaleResponse> updatePayments(
            @PathVariable String salesId,
            @RequestParam BigDecimal cash,
            @RequestParam BigDecimal upi,
            @RequestParam BigDecimal credit
    ) {
        Sales updatedSale = salesService.updatePayments(salesId, cash, upi, credit);
        if (updatedSale == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(new SaleResponse(updatedSale));
    }

    // DTO for response
    static class SaleResponse {
        private String salesId;
        private List<SalesItem> items;
        private BigDecimal cash;
        private BigDecimal upi;
        private BigDecimal credit;

        public SaleResponse(Sales sale) {
            this.salesId = sale.getSalesId();
            this.items = sale.getItems();
            this.cash = sale.getCash();
            this.upi = sale.getUpi();
            this.credit = sale.getCredit();
        }

        public String getSalesId() { return salesId; }
        public List<SalesItem> getItems() { return items; }
        public BigDecimal getCash() { return cash; }
        public BigDecimal getUpi() { return upi; }
        public BigDecimal getCredit() { return credit; }
    }
}

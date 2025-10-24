package com.ShopApp.AppZone.controller;


import com.ShopApp.AppZone.model.BillingItem;
import com.ShopApp.AppZone.service.BillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/billing")
@CrossOrigin(origins = "*")
public class BillingController {

    @Autowired
    private BillingService service;

    // Get all billing items
    @GetMapping("/list")
    public List<BillingItem> getAllItems() {
        return service.getAllItems();
    }

    // Add a billing item
    @PostMapping("/add")
    public BillingItem addItem(@RequestBody BillingItem item) {
        // Optional: validate verificationId
        if (item.getVerificationId() == null || item.getVerificationId().isEmpty()) {
            throw new IllegalArgumentException("Verification ID is required");
        }
        return service.saveItem(item);
    }

    // Optional: Add endpoint to fetch billing item by verificationId
    @GetMapping("/verification/{verificationId}")
    public BillingItem getItemByVerificationId(@PathVariable String verificationId) {
        return service.getAllItems()
                .stream()
                .filter(b -> verificationId.equals(b.getVerificationId()))
                .findFirst()
                .orElse(null);
    }

    // Optional: Delete a billing item
    @DeleteMapping("/delete/{id}")
    public String deleteItem(@PathVariable Long id) {
        service.deleteItem(id);
        return "Billing item deleted successfully";
    }
}

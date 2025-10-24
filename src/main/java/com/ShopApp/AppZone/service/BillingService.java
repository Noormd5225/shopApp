package com.ShopApp.AppZone.service;

import com.ShopApp.AppZone.model.BillingItem;
import com.ShopApp.AppZone.repository.BillingItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillingService {

    @Autowired
    private BillingItemRepository repository;

    // Get all items
    public List<BillingItem> getAllItems() {
        return repository.findAll();
    }

    // Save an item
    public BillingItem saveItem(BillingItem item) {
        return repository.save(item);
    }

    // Delete an item by ID
    public void deleteItem(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Billing item with ID " + id + " does not exist");
        }
        repository.deleteById(id);
    }
}

package com.ShopApp.AppZone.service;


import com.ShopApp.AppZone.model.ItemClassification;
import com.ShopApp.AppZone.repository.ItemClassificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemClassificationService {
    @Autowired
    private ItemClassificationRepository repo;

    public List<ItemClassification> getAll() {
        return repo.findAll();
    }

    public ItemClassification getById(String id) {
        return repo.findById(id).orElse(null);
    }

    public ItemClassification create(ItemClassification item) {
        // Find last classification for this product
        String productId = item.getProductId();
        String lastId = repo.findAll().stream()
                .filter(i -> i.getProductId().equals(productId))
                .map(ItemClassification::getClassificationId)
                .max(String::compareTo)
                .orElse(null);

        int nextNum = 1;
        if (lastId != null && lastId.length() > productId.length() + 2) {
            // Extract the numeric part after productId + "IC"
            String numStr = lastId.substring(productId.length() + 2);
            nextNum = Integer.parseInt(numStr) + 1;
        }
        String newId = productId + "IC" + String.format("%03d", nextNum);
        item.setClassificationId(newId);

        return repo.save(item);
    }

    public ItemClassification update(String id, ItemClassification item) {
        item.setClassificationId(id);
        return repo.save(item);
    }

    public void delete(String id) {
        repo.deleteById(id);
    }
}

package com.ShopApp.AppZone.controller;

import com.ShopApp.AppZone.model.ItemClassification;
import com.ShopApp.AppZone.service.ItemClassificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/classifications")
@CrossOrigin(origins = "*")
public class ItemClassificationController {
    @Autowired
    private ItemClassificationService service;

    @GetMapping
    public List<ItemClassification> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ItemClassification getById(@PathVariable String id) {
        return service.getById(id);
    }

    @PostMapping
    public ItemClassification create(@RequestBody ItemClassification item) {
        return service.create(item);
    }


    @GetMapping("/list")
    public List<Map<String, String>> getClassificationList() {
        List<ItemClassification> classifications = service.getAll();
        List<Map<String, String>> result = new ArrayList<>();
        for (ItemClassification ic : classifications) {
            Map<String, String> map = new HashMap<>();
            map.put("classificationId", ic.getClassificationId());
            map.put("name", ic.getName());
            result.add(map);
        }
        return result;
    }


    @GetMapping("/product-classification-list")
    public List<Map<String, String>> getProductClassificationList() {
        List<ItemClassification> classifications = service.getAll();
        List<Map<String, String>> result = new ArrayList<>();

        for (ItemClassification ic : classifications) {
            Map<String, String> map = new HashMap<>();
            map.put("productId", ic.getProductId()); // productId associated with this classification
            map.put("classificationId", ic.getClassificationId());
            map.put("name", ic.getName());
            result.add(map);
        }

        return result;
    }



    @PutMapping("/{id}")
    public ItemClassification update(@PathVariable String id, @RequestBody ItemClassification item) {
        return service.update(id, item);
    }
    // New API to get a specific discount by classificationId and type
    @GetMapping("/discount/{classificationId}")
    public Map<String, Object> getDiscount(
            @PathVariable String classificationId,
            @RequestParam String type) {

        ItemClassification ic = service.getById(classificationId);
        Map<String, Object> response = new HashMap<>();

        if (ic == null) {
            response.put("error", "Classification not found");
            return response;
        }

        double discountValue;
        switch (type.toLowerCase()) {
            case "discount1":
                discountValue = ic.getDiscount1();
                break;
            case "discount2":
                discountValue = ic.getDiscount2();
                break;
            case "discount3":
                discountValue = ic.getDiscount3();
                break;
            default:
                response.put("error", "Invalid discount type. Use discount1, discount2, or discount3.");
                return response;
        }

        response.put("classificationId", classificationId);
        response.put("discountType", type.toLowerCase());
        response.put("discountValue", discountValue);
        return response;
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        service.delete(id);
    }
}


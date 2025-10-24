package com.ShopApp.AppZone.controller;


import com.ShopApp.AppZone.model.VerifiedItem;
import com.ShopApp.AppZone.repository.VerifiedItemRepository;
import com.ShopApp.AppZone.service.VerifiedItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/discount-offer")
@CrossOrigin(origins = "*")
public class DiscountOfferController {

    @Autowired
    private VerifiedItemService verifiedItemService;

    @Autowired
    private VerifiedItemRepository verifiedItemRepository;

    // ✅ Get all online shop items with limited fields
    @GetMapping("/online-items")
    public ResponseEntity<List<Map<String, Object>>> getOnlineItems() {
        List<VerifiedItem> items = verifiedItemService.getAll().stream()
                .filter(item -> "online shop".equalsIgnoreCase(item.getSalesType()))
                .collect(Collectors.toList());

        if (items.isEmpty()) return ResponseEntity.noContent().build();

        List<Map<String, Object>> response = new ArrayList<>();
        for (VerifiedItem item : items) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("verification", item.getVerification());
            map.put("specification", item.getSpecification());
            map.put("rate", item.getRate());
            map.put("sellingCost", item.getSellingCost());
            map.put("discount1", item.getDiscount1());
            map.put("discount2", item.getDiscount2());
            map.put("discount3", item.getDiscount3());
            response.add(map);
        }

        return ResponseEntity.ok(response);
    }

    // ✅ Update discount only
    @PostMapping("/update-discount/{verificationId}")
    public ResponseEntity<VerifiedItem> updateDiscount(
            @PathVariable String verificationId,
            @RequestParam(required = false) Double discount1,
            @RequestParam(required = false) Double discount2,
            @RequestParam(required = false) Double discount3
    ) {
        Optional<VerifiedItem> optionalItem = verifiedItemRepository.findByVerification(verificationId);

        if (optionalItem.isEmpty()) return ResponseEntity.notFound().build();

        VerifiedItem item = optionalItem.get();

        if (discount1 != null) item.setDiscount1(discount1);
        if (discount2 != null) item.setDiscount2(discount2);
        if (discount3 != null) item.setDiscount3(discount3);

        VerifiedItem saved = verifiedItemService.save(item);
        return ResponseEntity.ok(saved);
    }
}

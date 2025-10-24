package com.ShopApp.AppZone.controller;

import com.ShopApp.AppZone.model.ItemClassification;
import com.ShopApp.AppZone.model.VerifiedItem;
import com.ShopApp.AppZone.repository.VerifiedItemRepository;
import com.ShopApp.AppZone.service.ItemClassificationService;
import com.ShopApp.AppZone.service.VerifiedItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

import com.ShopApp.AppZone.model.VerifiedItem;
import com.ShopApp.AppZone.service.VerifiedItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/verify")
@CrossOrigin(origins = "*")
public class VerifyController {

    @Autowired
    private VerifiedItemService verifiedItemService;

    @Autowired
    VerifiedItemRepository verifiedItemRepository;




    @PostMapping("/{purchaseId}")
    public ResponseEntity<List<VerifiedItem>> verifyPurchase(@PathVariable String purchaseId) {
        List<VerifiedItem> verifiedItems = verifiedItemService.generateFromPurchaseItemsByPurchaseId(purchaseId);
        if (verifiedItems.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(verifiedItems);
    }

    @GetMapping("/all")
    public List<VerifiedItem> getAllVerifiedItems() {
        return verifiedItemService.getAll();
    }

    @GetMapping("/{purchaseId}")
    public ResponseEntity<List<VerifiedItem>> getVerifiedItems(@PathVariable String purchaseId) {
        List<VerifiedItem> verifiedItems = verifiedItemService.getByPurchaseId(purchaseId);
        if (verifiedItems.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(verifiedItems);
    }

    @GetMapping("/individual-stock")
    public ResponseEntity<List<Map<String, Object>>> getAllIndividualStock() {
        List<VerifiedItem> items = verifiedItemService.getAll(); // get all items
        if (items.isEmpty()) return ResponseEntity.noContent().build();

        List<Map<String, Object>> response = new ArrayList<>();
        int serial = 1;
        for (VerifiedItem item : items) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("serial", serial++); // manual serial number
            map.put("quantity", item.getQuantity());
            map.put("rate", item.getRate());
            map.put("sellingCost", item.getSellingCost());
            map.put("discount1", item.getDiscount1());
            map.put("discount2", item.getDiscount2());
            map.put("discount3", item.getDiscount3());
            map.put("hotsale1", item.getHotsale1());
            map.put("hotsale2", item.getHotsale2());
            map.put("salesType", item.getSalesType());
            map.put("specification", item.getSpecification());
            response.add(map);
        }

        return ResponseEntity.ok(response);
    }


    // Get all verified items where salesType = "online"
    @GetMapping("/online-stock")
    public ResponseEntity<List<VerifiedItem>> getOnlineStock() {
        List<VerifiedItem> items = verifiedItemService.getAll().stream()
                .filter(item -> "online shop".equalsIgnoreCase(item.getSalesType()))
                .toList();

        if (items.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(items);
    }

    // Get all verified items where salesType = "shop"
    @GetMapping("/shop-stock")
    public ResponseEntity<List<VerifiedItem>> getShopStock() {
        List<VerifiedItem> items = verifiedItemService.getAll().stream()
                .filter(item -> "shop".equalsIgnoreCase(item.getSalesType()))
                .toList();

        if (items.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(items);
    }
    @GetMapping("/all/{purchaseId}")
    public ResponseEntity<List<VerifiedItem>> getAllByPurchaseId(@PathVariable String purchaseId) {
        List<VerifiedItem> items = verifiedItemService.getByPurchaseId(purchaseId);
        if (items.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(items);
    }

    // ✅ Update item with image support using POST
    @PostMapping("/update/{id}")
    public ResponseEntity<VerifiedItem> updateItem(
            @PathVariable String id,
            @RequestParam(required = false) Double sellingCost,
            @RequestParam(required = false) Double discount1,
            @RequestParam(required = false) Double discount2,
            @RequestParam(required = false) Double discount3,
            @RequestParam(required = false) Double hotsale1,
            @RequestParam(required = false) Double hotsale2,
            @RequestParam(required = false) String salesType,
            @RequestParam(required = false) String specification,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) MultipartFile itemImage
    ) throws IOException {

        Optional<VerifiedItem> optional = verifiedItemService.getById(id);
        if (!optional.isPresent()) return ResponseEntity.notFound().build();

        VerifiedItem item = optional.get();

        // Update fields if provided
        if (sellingCost != null) item.setSellingCost(sellingCost);
        if (discount1 != null) item.setDiscount1(discount1);
        if (discount2 != null) item.setDiscount2(discount2);
        if (discount3 != null) item.setDiscount3(discount3);
        if (hotsale1 != null) item.setHotsale1(hotsale1);
        if (hotsale2 != null) item.setHotsale2(hotsale2);
        if (salesType != null) item.setSalesType(salesType);
        if (specification != null) item.setSpecification(specification);
        if (status != null) item.setStatus(status);

        // ✅ Update image like your upload page
        if (itemImage != null && !itemImage.isEmpty()) {
            String uploadDir = System.getProperty("user.dir") + "/uploads/";
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();

            String filename = UUID.randomUUID() + "_" + Objects.requireNonNull(itemImage.getOriginalFilename());
            File dest = new File(uploadDir + filename);
            itemImage.transferTo(dest);

            // Save relative path in DB
            item.setItemImage("/uploads/" + filename);
        }

        VerifiedItem saved = verifiedItemService.save(item);
        return ResponseEntity.ok(saved);
    }


    @GetMapping("/rate/{id}")
    public ResponseEntity<Double> getRateById(@PathVariable String id) {
        Optional<VerifiedItem> optional = verifiedItemService.getById(id);
        if (!optional.isPresent()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(optional.get().getRate());
    }

    @GetMapping("/discount/{id}")
    public ResponseEntity<Double> getDiscountByIdAndType(
            @PathVariable String id,
            @RequestParam String type) {

        Optional<VerifiedItem> optional = verifiedItemService.getById(id);
        if (!optional.isPresent()) return ResponseEntity.notFound().build();

        VerifiedItem item = optional.get();
        double discount;
        switch (type.toLowerCase()) {
            case "discount1": discount = item.getDiscount1(); break;
            case "discount2": discount = item.getDiscount2(); break;
            case "discount3": discount = item.getDiscount3(); break;
            default: return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(discount);
    }
    @GetMapping("/rate/{verificationId}")
    public Map<String, Object> getRateByVerification(@PathVariable String verificationId) {
        Map<String, Object> response = new HashMap<>();
        Optional<VerifiedItem> itemOpt = verifiedItemRepository.findByVerification(verificationId);

        if (itemOpt.isPresent()) {
            response.put("verificationId", verificationId);
            response.put("rate", itemOpt.get().getRate());
        } else {
            response.put("error", "Verification ID not found");
        }

        return response;
    }


    @GetMapping("/get-rate/{verificationId}")
    public ResponseEntity<Map<String, Object>> getRateByVerificationId(@PathVariable String verificationId) {
        Optional<VerifiedItem> optionalItem = verifiedItemRepository.findByVerification(verificationId);

        if (optionalItem.isEmpty()) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Verification ID not found");
            return ResponseEntity.status(404).body(errorResponse);
        }

        VerifiedItem item = optionalItem.get();
        Map<String, Object> response = new HashMap<>();
        response.put("verificationId", verificationId);
        response.put("rate", item.getRate());
        response.put("sellingCost", item.getSellingCost());
        response.put("discount1", item.getDiscount1());
        response.put("discount2", item.getDiscount2());
        response.put("discount3", item.getDiscount3());
        response.put("hotsale1", item.getHotsale1());
        response.put("hotsale2", item.getHotsale2());
        response.put("salesType", item.getSalesType());
        response.put("specification", item.getSpecification());
        response.put("quantity", item.getQuantity());

        return ResponseEntity.ok(response);
    }


    @Autowired
    private ItemClassificationService itemClassificationService;

    // Get verification details with auto quantity rules
    @GetMapping("/verification-info/{verificationId}")
    public ResponseEntity<Map<String, Object>> getVerificationInfo(@PathVariable String verificationId) {
        Optional<VerifiedItem> optionalItem = verifiedItemRepository.findByVerification(verificationId);
        if (optionalItem.isEmpty()) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Verification ID not found");
            return ResponseEntity.status(404).body(error);
        }

        VerifiedItem item = optionalItem.get();

        ItemClassification ic = itemClassificationService.getById(item.getItemClassificationId());
        if (ic == null) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Item classification not found");
            return ResponseEntity.status(404).body(error);
        }

        int autoQuantity = 0;
        boolean inputQuantityRequired = true;

        if (ic.getDifferenceNumber() == 1 && ic.getPackedQuantity() == 1) {
            autoQuantity = 1;
            inputQuantityRequired = false;
        }

        Map<String, Object> response = new HashMap<>();
        response.put("verificationId", item.getVerification());
        response.put("sellingCost", item.getSellingCost());
        response.put("itemClassificationId", ic.getClassificationId());
        response.put("differenceNumber", ic.getDifferenceNumber());
        response.put("packedQuantity", ic.getPackedQuantity());
        response.put("autoQuantity", autoQuantity);
        response.put("inputQuantityRequired", inputQuantityRequired);

        return ResponseEntity.ok(response);
    }




    // ✅ Get all items with fields needed for display
    @GetMapping("/display-all")
    public ResponseEntity<List<Map<String, Object>>> displayAllItems() {
        List<VerifiedItem> items = verifiedItemRepository.findAll();
        if (items.isEmpty()) return ResponseEntity.noContent().build();

        List<Map<String, Object>> response = new ArrayList<>();
        for (VerifiedItem item : items) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("id", item.getId());
            map.put("verification", item.getVerification());
            map.put("specification", item.getSpecification());
            map.put("rate", item.getRate());
            map.put("sellingCost", item.getSellingCost());
            map.put("onlineDiscount", item.getOnlineDiscount());
            response.add(map);
        }

        return ResponseEntity.ok(response);
    }

    // ✅ Update onlineDiscount by verification ID
    @PostMapping("/update-online-discount")
    public ResponseEntity<Map<String, Object>> updateOnlineDiscount(
            @RequestParam String verification,
            @RequestParam double onlineDiscount) {

        Optional<VerifiedItem> optionalItem = verifiedItemRepository.findByVerification(verification);
        if (optionalItem.isEmpty()) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Verification ID not found");
            return ResponseEntity.status(404).body(error);
        }

        VerifiedItem item = optionalItem.get();
        item.setOnlineDiscount(onlineDiscount);
        verifiedItemRepository.save(item);

        Map<String, Object> response = new HashMap<>();
        response.put("verification", verification);
        response.put("onlineDiscount", onlineDiscount);
        response.put("message", "Online discount updated successfully");
        return ResponseEntity.ok(response);
    }





    // ✅ Get specification by verification ID
    @GetMapping("/specification/{verificationId}")
    public ResponseEntity<Map<String, Object>> getSpecificationByVerification(
            @PathVariable String verificationId) {

        Optional<VerifiedItem> optionalItem = verifiedItemRepository.findByVerification(verificationId);

        if (optionalItem.isEmpty()) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Verification ID not found");
            return ResponseEntity.status(404).body(errorResponse);
        }

        VerifiedItem item = optionalItem.get();
        Map<String, Object> response = new HashMap<>();
        response.put("verificationId", verificationId);
        response.put("specification", item.getSpecification());

        return ResponseEntity.ok(response);
    }


    // Get sellingCost by verification ID
    @GetMapping("/selling-cost/{verificationId}")
    public ResponseEntity<Map<String, Object>> getSellingCostByVerification(@PathVariable String verificationId) {
        Optional<VerifiedItem> optionalItem = verifiedItemRepository.findByVerification(verificationId);

        if (optionalItem.isEmpty()) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Verification ID not found");
            return ResponseEntity.status(404).body(errorResponse);
        }

        VerifiedItem item = optionalItem.get();
        Map<String, Object> response = new HashMap<>();
        response.put("verificationId", verificationId);
        response.put("sellingCost", item.getSellingCost());
        return ResponseEntity.ok(response);
    }


    // ✅ Reduce quantity by verificationId
    @PostMapping("/reduce-quantity")
    public ResponseEntity<Map<String, Object>> reduceQuantity(
            @RequestParam String verificationId,
            @RequestParam int quantityToReduce) {

        Optional<VerifiedItem> optionalItem = verifiedItemRepository.findByVerification(verificationId);

        if (optionalItem.isEmpty()) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Verification ID not found");
            return ResponseEntity.status(404).body(error);
        }

        VerifiedItem item = optionalItem.get();

        // Check stock availability
        if (item.getQuantity() < quantityToReduce) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Not enough stock available");
            error.put("availableQuantity", item.getQuantity());
            return ResponseEntity.status(400).body(error);
        }

        // Deduct quantity
        item.setQuantity(item.getQuantity() - quantityToReduce);
        verifiedItemService.save(item);

        Map<String, Object> response = new HashMap<>();
        response.put("verificationId", verificationId);
        response.put("reducedQuantity", quantityToReduce);
        response.put("remainingQuantity", item.getQuantity());

        return ResponseEntity.ok(response);
    }

    // ✅ Increase quantity by verificationId
    @PostMapping("/add-quantity")
    public ResponseEntity<Map<String, Object>> addQuantity(
            @RequestParam String verificationId,
            @RequestParam int quantityToAdd) {

        Optional<VerifiedItem> optionalItem = verifiedItemRepository.findByVerification(verificationId);

        if (optionalItem.isEmpty()) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Verification ID not found");
            return ResponseEntity.status(404).body(error);
        }

        VerifiedItem item = optionalItem.get();

        // Add quantity
        item.setQuantity(item.getQuantity() + quantityToAdd);
        verifiedItemService.save(item);

        Map<String, Object> response = new HashMap<>();
        response.put("verificationId", verificationId);
        response.put("addedQuantity", quantityToAdd);
        response.put("currentQuantity", item.getQuantity());

        return ResponseEntity.ok(response);
    }






}



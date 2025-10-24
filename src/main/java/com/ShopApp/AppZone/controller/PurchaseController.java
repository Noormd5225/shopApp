package com.ShopApp.AppZone.controller;

import com.ShopApp.AppZone.model.Purchase;
import com.ShopApp.AppZone.model.PurchaseItem;
import com.ShopApp.AppZone.service.PurchaseItemService;
import com.ShopApp.AppZone.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;  

@RestController
@RequestMapping("/api/purchases")
@CrossOrigin(origins = "*")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private PurchaseItemService purchaseItemService;

    // ✅ Create a new purchase
    @PostMapping
    public ResponseEntity<Purchase> create(@RequestBody Purchase request) {
        Purchase saved = purchaseService.createPurchase(request);
        return ResponseEntity.ok(saved);
    }

    // ✅ Get all purchases
    @GetMapping
    public ResponseEntity<List<Purchase>> getAll() {
        return ResponseEntity.ok(purchaseService.getAllPurchases());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Purchase> updatePurchase(@PathVariable String id, @RequestBody Purchase updatedData) {
        return purchaseService.getPurchase(id) != null
                ? ResponseEntity.ok(purchaseService.updatePurchase(id, updatedData))
                : ResponseEntity.notFound().build();
    }
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Purchase>> getPurchasesByStatus(@PathVariable String status) {
        List<Purchase> purchases = purchaseService.getPurchasesByStatus(status);
        return ResponseEntity.ok(purchases);
    }
    // ✅ Get purchase by ID
    @GetMapping("/{id}")
    public ResponseEntity<Purchase> getById(@PathVariable String id) {
        Purchase purchase = purchaseService.getPurchase(id);
        return purchase != null ? ResponseEntity.ok(purchase) : ResponseEntity.notFound().build();
    }

    // ✅ Delete purchase
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        boolean deleted = purchaseService.deletePurchase(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // ✅ ---- PurchaseItem APIs ----
    @GetMapping("/{purchaseId}/items")
    public ResponseEntity<List<PurchaseItem>> getItemsByPurchase(@PathVariable String purchaseId) {
        return ResponseEntity.ok(purchaseItemService.getItemsByPurchaseId(purchaseId));
    }

    @PostMapping("/{purchaseId}/items")
    public ResponseEntity<PurchaseItem> addItem(@PathVariable String purchaseId, @RequestBody PurchaseItem item) {
        PurchaseItem saved = purchaseItemService.addItemToPurchase(purchaseId, item);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/items/{itemId}")
    public ResponseEntity<PurchaseItem> updateItem(@PathVariable String itemId, @RequestBody PurchaseItem item) {
        PurchaseItem updated = purchaseItemService.updateItem(itemId, item);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<Void> deleteItem(@PathVariable String itemId) {
        boolean deleted = purchaseItemService.deleteItem(itemId);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // ✅ Generic API: Update status
    @PutMapping("/{id}/status")
    public ResponseEntity<Purchase> updateStatus(@PathVariable String id, @RequestParam String status) {
        Purchase updated = purchaseService.updateStatus(id, status);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    // ✅ New API: Directly set status = "verified"
    @PutMapping("/{id}/verify")
    public ResponseEntity<Purchase> verifyPurchase(@PathVariable String id) {
        Purchase updated = purchaseService.updateStatus(id, "verified");
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }
}

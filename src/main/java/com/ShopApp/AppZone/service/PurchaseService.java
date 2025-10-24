package com.ShopApp.AppZone.service;

import com.ShopApp.AppZone.model.Purchase;
import com.ShopApp.AppZone.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepo;

    private String generateNextPurchaseId() {
        List<String> ids = purchaseRepo.findAllPurchaseIdsOrderedDesc();
        if (ids == null || ids.isEmpty()) {
            return "P001";
        }
        String lastId = ids.get(0);
        String numPart = lastId.replaceAll("\\D", "");
        int number = numPart.isEmpty() ? 0 : Integer.parseInt(numPart);
        number++;
        return String.format("P%03d", number);
    }

    @Transactional
    public Purchase createPurchase(Purchase purchase) {
        if (purchase.getPurchaseId() == null || purchase.getPurchaseId().trim().isEmpty()) {
            String newId = generateNextPurchaseId();
            purchase.setPurchaseId(newId);
        }

        // ✅ Ensure default status is set if not provided
        if (purchase.getStatus() == null || purchase.getStatus().trim().isEmpty()) {
            purchase.setStatus("not verified");
        }

        if (purchase.getItems() != null) {
            purchase.getItems().forEach(item -> item.setPurchase(purchase));
        }
        return purchaseRepo.save(purchase);
    }

    public List<Purchase> getAllPurchases() {
        return purchaseRepo.findAll();
    }
    @Transactional
    public Purchase updatePurchase(String id, Purchase updatedData) {
        return purchaseRepo.findById(id).map(p -> {
            p.setSupplierId(updatedData.getSupplierId());
            p.setBillNumber(updatedData.getBillNumber());
            p.setBillDate(updatedData.getBillDate());
            return purchaseRepo.save(p);
        }).orElse(null);
    }


    public List<Purchase> getPurchasesByStatus(String status) {
        return purchaseRepo.findByStatus(status);
    }
    public Purchase getPurchase(String id) {
        return purchaseRepo.findById(id).orElse(null);
    }

    public boolean deletePurchase(String id) {
        if (purchaseRepo.existsById(id)) {
            purchaseRepo.deleteById(id);
            return true;
        }
        return false;
    }

    // ✅ New method to update status
    @Transactional
    public Purchase updateStatus(String id, String status) {
        return purchaseRepo.findById(id).map(purchase -> {
            purchase.setStatus(status);
            return purchaseRepo.save(purchase);
        }).orElse(null);
    }
}

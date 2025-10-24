package com.ShopApp.AppZone.controller;



import com.ShopApp.AppZone.model.Purchase;
import com.ShopApp.AppZone.model.PurchaseItem;
import com.ShopApp.AppZone.repository.PurchaseItemRepository;
import com.ShopApp.AppZone.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/purchases")
public class PurchaseVerifyController {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private PurchaseItemRepository purchaseItemRepository;

    // DTO for sending required info
    public static class PurchaseDTO {
        private String purchaseId;
        private String supplierName;
        private List<String> itemClassifications;
        private String billNumber;

        public PurchaseDTO(String purchaseId, String supplierName, List<String> itemClassifications, String billNumber) {
            this.purchaseId = purchaseId;
            this.supplierName = supplierName;
            this.itemClassifications = itemClassifications;
            this.billNumber = billNumber;
        }

        // getters
        public String getPurchaseId() { return purchaseId; }
        public String getSupplierName() { return supplierName; }
        public List<String> getItemClassifications() { return itemClassifications; }
        public String getBillNumber() { return billNumber; }
    }

    @GetMapping("/summary")
    public List<PurchaseDTO> getPurchaseSummary() {
        List<Purchase> purchases = purchaseRepository.findAll();

        List<PurchaseDTO> result = new ArrayList<>();

        for (Purchase purchase : purchases) {
            // Fetch items
            List<PurchaseItem> items = purchaseItemRepository.findByPurchase_PurchaseId(purchase.getPurchaseId());
            List<String> classifications = items.stream()
                    .map(PurchaseItem::getItemClassificationId)
                    .distinct()
                    .collect(Collectors.toList());

            // For demo, using supplierId as supplierName
            String supplierName = purchase.getSupplierId();

            result.add(new PurchaseDTO(
                    purchase.getPurchaseId(),
                    supplierName,
                    classifications,
                    purchase.getBillNumber()
            ));
        }

        return result;
    }
}

package com.ShopApp.AppZone.repository;

import com.ShopApp.AppZone.model.Purchase;
import com.ShopApp.AppZone.model.PurchaseItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseItemRepository extends JpaRepository<PurchaseItem, String> {
    // NEW: method to get all items by purchaseId
    List<PurchaseItem> findByPurchase_PurchaseId(String purchaseId);
}

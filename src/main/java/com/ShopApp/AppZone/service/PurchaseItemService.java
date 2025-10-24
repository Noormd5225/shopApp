package com.ShopApp.AppZone.service;


import com.ShopApp.AppZone.model.Purchase;
import com.ShopApp.AppZone.model.PurchaseItem;
import com.ShopApp.AppZone.repository.PurchaseItemRepository;
import com.ShopApp.AppZone.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseItemService {

    @Autowired
    private PurchaseItemRepository itemRepo;

    @Autowired
    private PurchaseRepository purchaseRepo;

    public List<PurchaseItem> getItemsByPurchaseId(String purchaseId) {
        return itemRepo.findByPurchase_PurchaseId(purchaseId);
    }


    public PurchaseItem addItemToPurchase(String purchaseId, PurchaseItem item) {
        Purchase purchase = purchaseRepo.findById(purchaseId).orElse(null);
        if (purchase == null) return null;
        item.setPurchase(purchase);
        return itemRepo.save(item);
    }

    public PurchaseItem updateItem(String itemId, PurchaseItem newData) {
        return itemRepo.findById(itemId).map(item -> {
            item.setProductCategoryId(newData.getProductCategoryId());
            item.setProductId(newData.getProductId());
            item.setItemClassificationId(newData.getItemClassificationId());
            item.setQuantity(newData.getQuantity());
            item.setRate(newData.getRate());
            item.setSgst(newData.getSgst());
            item.setCgst(newData.getCgst());
            item.setIgst(newData.getIgst());
            return itemRepo.save(item);
        }).orElse(null);
    }

    public boolean deleteItem(String itemId) {
        if (itemRepo.existsById(itemId)) {
            itemRepo.deleteById(itemId);
            return true;
        }
        return false;
    }
}

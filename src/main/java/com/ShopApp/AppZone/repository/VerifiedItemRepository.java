package com.ShopApp.AppZone.repository;


import com.ShopApp.AppZone.model.VerifiedItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VerifiedItemRepository extends JpaRepository<VerifiedItem, String> {
    List<VerifiedItem> findByPurchaseId(String purchaseId);
    List<VerifiedItem> findByItemClassificationId(String itemClassificationId);
   Optional< VerifiedItem> findByVerification(String verification);


}

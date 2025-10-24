package com.ShopApp.AppZone.repository;

import com.ShopApp.AppZone.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, String> {

    /**
     * Return purchase IDs ordered by length descending and lexicographically descending,
     * so "P100" > "P99" etc.
     */
    @Query("SELECT p.purchaseId FROM Purchase p ORDER BY LENGTH(p.purchaseId) DESC, p.purchaseId DESC")
    List<String> findAllPurchaseIdsOrderedDesc();
    List<Purchase> findByStatus(String status);

}

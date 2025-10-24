package com.ShopApp.AppZone.repository;




import com.ShopApp.AppZone.model.BillingItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillingItemRepository extends JpaRepository<BillingItem, Long> {
    boolean existsByBillNumber(String billNumber);
}

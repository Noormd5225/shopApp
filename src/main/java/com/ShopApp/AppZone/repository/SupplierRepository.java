package com.ShopApp.AppZone.repository;


import com.ShopApp.AppZone.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, String> {

    @Query(value = "SELECT supplier_id FROM Supplier ORDER BY supplier_id DESC LIMIT 1", nativeQuery = true)
    String findLastSupplierId();
}
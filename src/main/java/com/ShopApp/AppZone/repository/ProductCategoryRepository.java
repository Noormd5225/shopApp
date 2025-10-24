package com.ShopApp.AppZone.repository;


import com.ShopApp.AppZone.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, String> {

    @Query(value = "SELECT category_id FROM product_category ORDER BY category_id DESC LIMIT 1", nativeQuery = true)
    String findLastCategoryId();
}

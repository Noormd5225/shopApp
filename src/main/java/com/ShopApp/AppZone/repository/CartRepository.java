package com.ShopApp.AppZone.repository;



import com.ShopApp.AppZone.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByCustomerId(String customerId);
    List<Cart> findByProductId(String productId);

}

package com.ShopApp.AppZone.repository;
import com.ShopApp.AppZone.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {




    // Find customer by mobile number
    Customer findByMobileNo(String mobileNo);




    @Query(value = "SELECT customer_id FROM customer ORDER BY customer_id DESC LIMIT 1", nativeQuery = true)
    String getLastCustomerId();
}

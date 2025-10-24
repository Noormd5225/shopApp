package com.ShopApp.AppZone.repository;




import com.ShopApp.AppZone.model.Sales;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface SalesRepository extends JpaRepository<Sales, String> {
    long countBySalesDate(LocalDate date);

    // Find by exact date
    List<Sales> findBySalesDate(LocalDate date);

    // Find between two dates
    List<Sales> findBySalesDateBetween(LocalDate start, LocalDate end);
}


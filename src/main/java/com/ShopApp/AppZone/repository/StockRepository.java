package com.ShopApp.AppZone.repository;


import com.ShopApp.AppZone.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


import com.ShopApp.AppZone.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


import com.ShopApp.AppZone.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    Optional<Stock> findByItemClassificationId(String itemClassificationId);
}

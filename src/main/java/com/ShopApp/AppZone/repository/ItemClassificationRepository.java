package com.ShopApp.AppZone.repository;


import com.ShopApp.AppZone.model.ItemClassification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemClassificationRepository extends JpaRepository<ItemClassification, String> {
}


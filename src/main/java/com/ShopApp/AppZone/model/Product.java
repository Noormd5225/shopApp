package com.ShopApp.AppZone.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Product {

    @Id
    private String productId;

    private String name;
    private String categoryId;  // only store categoryId, no mapping

    public Product() {}

    public Product(String productId, String name, String categoryId) {
        this.productId = productId;
        this.name = name;
        this.categoryId = categoryId;
    }

    // getters & setters
    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategoryId() { return categoryId; }
    public void setCategoryId(String categoryId) { this.categoryId = categoryId; }
}


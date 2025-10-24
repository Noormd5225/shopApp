package com.ShopApp.AppZone.model;


import jakarta.persistence.*;

@Entity
@Table(name = "stock")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stockId;

    private String itemClassificationId;
    private int quantity;

    public Stock() {}

    public Stock(String itemClassificationId, int quantity) {
        this.itemClassificationId = itemClassificationId;
        this.quantity = quantity;
    }

    public Long getStockId() {
        return stockId;
    }

    public String getItemClassificationId() {
        return itemClassificationId;
    }

    public void setItemClassificationId(String itemClassificationId) {
        this.itemClassificationId = itemClassificationId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

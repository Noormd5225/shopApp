package com.ShopApp.AppZone.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "purchase_items")
public class PurchaseItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "item_id", length = 36)
    private String itemId;

    @Column(name = "product_category_id")
    private String productCategoryId;

    @Column(name = "product_id")
    private String productId;

    @Column(name = "item_classification_id")
    private String itemClassificationId;

    private int quantity;
    private double rate;
    private double sgst;
    private double cgst;
    private double igst;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_id")
    @JsonBackReference
    private Purchase purchase;

    public PurchaseItem() {}

    // Getters / setters

    public String getItemId() { return itemId; }
    public void setItemId(String itemId) { this.itemId = itemId; }

    public String getProductCategoryId() { return productCategoryId; }
    public void setProductCategoryId(String productCategoryId) { this.productCategoryId = productCategoryId; }

    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }

    public String getItemClassificationId() { return itemClassificationId; }
    public void setItemClassificationId(String itemClassificationId) { this.itemClassificationId = itemClassificationId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getRate() { return rate; }
    public void setRate(double rate) { this.rate = rate; }

    public double getSgst() { return sgst; }
    public void setSgst(double sgst) { this.sgst = sgst; }

    public double getCgst() { return cgst; }
    public void setCgst(double cgst) { this.cgst = cgst; }

    public double getIgst() { return igst; }
    public void setIgst(double igst) { this.igst = igst; }

    public Purchase getPurchase() { return purchase; }
    public void setPurchase(Purchase purchase) { this.purchase = purchase; }
}

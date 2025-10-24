package com.ShopApp.AppZone.model;

import jakarta.persistence.*;

@Entity
@Table(name = "verified_items")
public class VerifiedItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "purchase_id")
    private String purchaseId;

    @Column(name = "product_category_id")
    private String productCategoryId;

    @Column(name = "product_id")
    private String productId;

    @Column(name = "item_classification_id")
    private String itemClassificationId;

    @Column(name = "item_id")
    private String itemId;

    private int quantity;
    private double rate;

    private double sellingCost = 0;
    private double discount1 = 0;
    private double discount2 = 0;
    private double discount3 = 0;
    private double hotsale1 = 0;
    private double hotsale2 = 0;

    private String itemImage = null;
    private String verification = "not verified";
    private String salesType = "shop";
    private String specification = "new product";
    private String barcode = "";

    // ✅ Existing field
    private String status = "new";

    // ✅ New field
    private double onlineDiscount = -1;

    public VerifiedItem() {}

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getPurchaseId() { return purchaseId; }
    public void setPurchaseId(String purchaseId) { this.purchaseId = purchaseId; }

    public String getProductCategoryId() { return productCategoryId; }
    public void setProductCategoryId(String productCategoryId) { this.productCategoryId = productCategoryId; }

    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }

    public String getItemClassificationId() { return itemClassificationId; }
    public void setItemClassificationId(String itemClassificationId) { this.itemClassificationId = itemClassificationId; }

    public String getItemId() { return itemId; }
    public void setItemId(String itemId) { this.itemId = itemId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getRate() { return rate; }
    public void setRate(double rate) { this.rate = rate; }

    public double getSellingCost() { return sellingCost; }
    public void setSellingCost(double sellingCost) { this.sellingCost = sellingCost; }

    public double getDiscount1() { return discount1; }
    public void setDiscount1(double discount1) { this.discount1 = discount1; }

    public double getDiscount2() { return discount2; }
    public void setDiscount2(double discount2) { this.discount2 = discount2; }

    public double getDiscount3() { return discount3; }
    public void setDiscount3(double discount3) { this.discount3 = discount3; }

    public double getHotsale1() { return hotsale1; }
    public void setHotsale1(double hotsale1) { this.hotsale1 = hotsale1; }

    public double getHotsale2() { return hotsale2; }
    public void setHotsale2(double hotsale2) { this.hotsale2 = hotsale2; }

    public String getItemImage() { return itemImage; }
    public void setItemImage(String itemImage) { this.itemImage = itemImage; }

    public String getVerification() { return verification; }
    public void setVerification(String verification) { this.verification = verification; }

    public String getSalesType() { return salesType; }
    public void setSalesType(String salesType) { this.salesType = salesType; }

    public String getSpecification() { return specification; }
    public void setSpecification(String specification) { this.specification = specification; }

    public String getBarcode() { return barcode; }
    public void setBarcode(String barcode) { this.barcode = barcode; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public double getOnlineDiscount() { return onlineDiscount; }
    public void setOnlineDiscount(double onlineDiscount) { this.onlineDiscount = onlineDiscount; }
}

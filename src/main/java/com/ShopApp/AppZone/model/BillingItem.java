package com.ShopApp.AppZone.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;


import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "billing_items")
public class BillingItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productCategoryId;
    private String productId;
    private String itemClassificationId;
    private Integer quantity;
    private Double rate;
    private Double discountValue;
    private String discountType;

    // New field
    private String verificationId;

    // Additional fields
    private String customerId = "C0034";
    private Double upiAmount = 0.0;
    private Double cash = 0.0;
    private Double card = 0.0;
    private LocalDateTime sellingDate = LocalDateTime.now();
    private String billNumber;
    private Double payableAmount;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getProductCategoryId() { return productCategoryId; }
    public void setProductCategoryId(String productCategoryId) { this.productCategoryId = productCategoryId; }

    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }

    public String getItemClassificationId() { return itemClassificationId; }
    public void setItemClassificationId(String itemClassificationId) { this.itemClassificationId = itemClassificationId; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public Double getRate() { return rate; }
    public void setRate(Double rate) { this.rate = rate; }

    public Double getDiscountValue() { return discountValue; }
    public void setDiscountValue(Double discountValue) { this.discountValue = discountValue; }

    public String getDiscountType() { return discountType; }
    public void setDiscountType(String discountType) { this.discountType = discountType; }

    public String getVerificationId() { return verificationId; }
    public void setVerificationId(String verificationId) { this.verificationId = verificationId; }

    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }

    public Double getUpiAmount() { return upiAmount; }
    public void setUpiAmount(Double upiAmount) { this.upiAmount = upiAmount; }

    public Double getCash() { return cash; }
    public void setCash(Double cash) { this.cash = cash; }

    public Double getCard() { return card; }
    public void setCard(Double card) { this.card = card; }

    public LocalDateTime getSellingDate() { return sellingDate; }
    public void setSellingDate(LocalDateTime sellingDate) { this.sellingDate = sellingDate; }

    public String getBillNumber() { return billNumber; }
    public void setBillNumber(String billNumber) { this.billNumber = billNumber; }

    public Double getPayableAmount() { return payableAmount; }
    public void setPayableAmount(Double payableAmount) { this.payableAmount = payableAmount; }
}

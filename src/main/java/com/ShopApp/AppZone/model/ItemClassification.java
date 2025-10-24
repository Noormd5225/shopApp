package com.ShopApp.AppZone.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "item_classification")
public class ItemClassification {
    @Id
    private String classificationId;

    private String name;
    private int differenceNumber;
    private int packedQuantity;
    private double discount1;
    private double discount2;
    private double discount3;
    private String productId; // linked to Product (no mapping, just store id)

    // getters and setters
    public String getClassificationId() { return classificationId; }
    public void setClassificationId(String classificationId) { this.classificationId = classificationId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getDifferenceNumber() { return differenceNumber; }
    public void setDifferenceNumber(int differenceNumber) { this.differenceNumber = differenceNumber; }

    public int getPackedQuantity() { return packedQuantity; }
    public void setPackedQuantity(int packedQuantity) { this.packedQuantity = packedQuantity; }

    public double getDiscount1() { return discount1; }
    public void setDiscount1(double discount1) { this.discount1 = discount1; }

    public double getDiscount2() { return discount2; }
    public void setDiscount2(double discount2) { this.discount2 = discount2; }

    public double getDiscount3() { return discount3; }
    public void setDiscount3(double discount3) { this.discount3 = discount3; }

    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }
}

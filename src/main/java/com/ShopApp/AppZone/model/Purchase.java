package com.ShopApp.AppZone.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "purchases")
public class Purchase {
    @Id
    @Column(name = "purchase_id", length = 10)
    private String purchaseId; // e.g., P001, P002

    @Column(name = "supplier_id")
    private String supplierId;

    @Column(name = "bill_date")
    private String billDate;

    @Column(name = "bill_number")
    private String billNumber;

    // ✅ New field with default value
    @Column(name = "status", nullable = false)
    private String status = "not verified";

    @OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<PurchaseItem> items = new ArrayList<>();

    public Purchase() {}

    // Getters and setters
    public String getPurchaseId() { return purchaseId; }
    public void setPurchaseId(String purchaseId) { this.purchaseId = purchaseId; }

    public String getSupplierId() { return supplierId; }
    public void setSupplierId(String supplierId) { this.supplierId = supplierId; }

    public String getBillDate() { return billDate; }
    public void setBillDate(String billDate) { this.billDate = billDate; }

    public String getBillNumber() { return billNumber; }
    public void setBillNumber(String billNumber) { this.billNumber = billNumber; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public List<PurchaseItem> getItems() { return items; }

    public void setItems(List<PurchaseItem> items) {
        this.items.clear();
        if (items != null) {
            items.forEach(item -> item.setPurchase(this));
            this.items.addAll(items);
        }
    }
}

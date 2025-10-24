package com.ShopApp.AppZone.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class SalesItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long salesItemId;

    @ManyToOne
    @JoinColumn(name = "salesId")
    private Sales sales;

    private String particulars;
    private Integer quantity;
    private BigDecimal sellingCost;
    private BigDecimal total;

    // Getters and Setters
    public Long getSalesItemId() { return salesItemId; }
    public void setSalesItemId(Long salesItemId) { this.salesItemId = salesItemId; }
    public Sales getSales() { return sales; }
    public void setSales(Sales sales) { this.sales = sales; }
    public String getParticulars() { return particulars; }
    public void setParticulars(String particulars) { this.particulars = particulars; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public BigDecimal getSellingCost() { return sellingCost; }
    public void setSellingCost(BigDecimal sellingCost) { this.sellingCost = sellingCost; }
    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }
}

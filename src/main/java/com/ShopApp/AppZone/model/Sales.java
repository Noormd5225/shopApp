package com.ShopApp.AppZone.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Sales {

    @Id
    private String salesId; // Alpha-numeric ID

    private String customerId;
    private LocalDate salesDate;

    private BigDecimal originalTotal;
    private BigDecimal totalDiscount;
    private BigDecimal grandTotal;
    private BigDecimal roundedTotal;

    @OneToMany(mappedBy = "sales", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<SalesItem> items;

    // Payment modes
    private BigDecimal cash;
    private BigDecimal upi;
    private BigDecimal credit;

    // Getters and Setters
    public String getSalesId() { return salesId; }
    public void setSalesId(String salesId) { this.salesId = salesId; }
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public LocalDate getSalesDate() { return salesDate; }
    public void setSalesDate(LocalDate salesDate) { this.salesDate = salesDate; }
    public BigDecimal getOriginalTotal() { return originalTotal; }
    public void setOriginalTotal(BigDecimal originalTotal) { this.originalTotal = originalTotal; }
    public BigDecimal getTotalDiscount() { return totalDiscount; }
    public void setTotalDiscount(BigDecimal totalDiscount) { this.totalDiscount = totalDiscount; }
    public BigDecimal getGrandTotal() { return grandTotal; }
    public void setGrandTotal(BigDecimal grandTotal) { this.grandTotal = grandTotal; }
    public BigDecimal getRoundedTotal() { return roundedTotal; }
    public void setRoundedTotal(BigDecimal roundedTotal) { this.roundedTotal = roundedTotal; }
    public List<SalesItem> getItems() { return items; }
    public void setItems(List<SalesItem> items) { this.items = items; }
    public BigDecimal getCash() { return cash; }
    public void setCash(BigDecimal cash) { this.cash = cash; }
    public BigDecimal getUpi() { return upi; }
    public void setUpi(BigDecimal upi) { this.upi = upi; }
    public BigDecimal getCredit() { return credit; }
    public void setCredit(BigDecimal credit) { this.credit = credit; }
}

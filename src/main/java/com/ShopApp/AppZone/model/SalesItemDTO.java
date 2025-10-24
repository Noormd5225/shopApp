package com.ShopApp.AppZone.model;



import java.math.BigDecimal;

public class SalesItemDTO {
    private String particulars;
    private Integer quantity;
    private BigDecimal sellingCost;
    private BigDecimal total;

    public SalesItemDTO() {}

    public String getParticulars() { return particulars; }
    public void setParticulars(String particulars) { this.particulars = particulars; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public BigDecimal getSellingCost() { return sellingCost; }
    public void setSellingCost(BigDecimal sellingCost) { this.sellingCost = sellingCost; }
    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }
}

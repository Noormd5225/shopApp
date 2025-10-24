package com.ShopApp.AppZone.model;


import java.time.LocalDateTime;

public class OrderSummaryDTO {
    private String orderId;
    private String customerName;
    private LocalDateTime orderDate;
    private double totalAmount;
    private String status;

    public OrderSummaryDTO(String orderId, String customerName, LocalDateTime orderDate,
                           double totalAmount, String status) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    // Getters
    public String getOrderId() { return orderId; }
    public String getCustomerName() { return customerName; }
    public LocalDateTime getOrderDate() { return orderDate; }
    public double getTotalAmount() { return totalAmount; }
    public String getStatus() { return status; }
}

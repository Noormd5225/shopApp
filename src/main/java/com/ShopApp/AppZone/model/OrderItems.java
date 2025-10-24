package com.ShopApp.AppZone.model;


import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "order_items")
public class OrderItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String orderId; // Auto-generated ORD001, ORD002...

    private String customerId;

    @ManyToOne
    @JoinColumn(name = "order_ref_id", nullable = false)
    private Order order;  // Reference to Order entity

    private LocalDateTime orderDate;
    private String transactionId;
    private double amount;
    private String status;
    private String trackingId;

    public OrderItems() {}

    public OrderItems(String orderId, String customerId, Order order, LocalDateTime orderDate,
                      String transactionId, double amount, String status, String trackingId) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.order = order;
        this.orderDate = orderDate;
        this.transactionId = transactionId;
        this.amount = amount;
        this.status = status;
        this.trackingId = trackingId;
    }

    // Getters and setters
    public Long getId() { return id; }
    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }
    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }
    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getTrackingId() { return trackingId; }
    public void setTrackingId(String trackingId) { this.trackingId = trackingId; }
}


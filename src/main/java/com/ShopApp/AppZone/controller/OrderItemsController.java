package com.ShopApp.AppZone.controller;



import com.ShopApp.AppZone.model.Order;
import com.ShopApp.AppZone.model.OrderItems;
import com.ShopApp.AppZone.model.OrderSummaryDTO;
import com.ShopApp.AppZone.repository.OrderItemsRepository;
import com.ShopApp.AppZone.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/order-items")
@CrossOrigin(origins = "*")
public class OrderItemsController {

    @Autowired
    private OrderItemsRepository orderItemsRepository;

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/next-order-id")
    public String getNextOrderId() {
        List<String> lastOrderList = orderItemsRepository.findLastOrderId();
        String newOrderId = "ORD001"; // default if no previous orders
        if (!lastOrderList.isEmpty()) {
            String lastOrderId = lastOrderList.get(0); // e.g., ORD005
            int lastNumber = Integer.parseInt(lastOrderId.substring(3));
            newOrderId = String.format("ORD%03d", lastNumber + 1);
        }
        return newOrderId;
    }
    // Insert OrderItem with auto-generated orderId
    @PostMapping("/create")
    public String createOrderItem(@RequestBody OrderItems orderItem) {
        // Validate that the order exists
        Optional<Order> orderOptional = orderRepository.findById(orderItem.getOrder().getId());
        if (!orderOptional.isPresent()) {
            return "Order not found";
        }

        orderItem.setOrder(orderOptional.get());
        orderItem.setOrderDate(LocalDateTime.now());

        // Use the orderId provided by frontend
        if (orderItem.getOrderId() == null || orderItem.getOrderId().isEmpty()) {
            return "Order ID is required";
        }

        orderItemsRepository.save(orderItem);
        return "OrderItem saved successfully with orderId: " + orderItem.getOrderId();
    }

// In OrderItemsController.java

    @GetMapping("/customer/join/{customerId}")
    public List<Order> getOrdersByCustomerJoin(@PathVariable String customerId) {
        return orderItemsRepository.findOrdersByCustomerUsingJoin(customerId);
    }
    // Update tracking ID for a specific orderId
    @PutMapping("/update-tracking/{orderId}")
    public String updateTrackingId(@PathVariable String orderId, @RequestBody OrderItems updatedItem) {
        Optional<OrderItems> orderItemOptional = orderItemsRepository.findByOrderId(orderId);
        if (!orderItemOptional.isPresent()) {
            return "OrderItem not found";
        }

        OrderItems orderItem = orderItemOptional.get();
        orderItem.setTrackingId(updatedItem.getTrackingId()); // set new tracking ID
        orderItemsRepository.save(orderItem);

        return "Tracking ID updated successfully for orderId: " + orderId;
    }



    // Get all OrderItems where trackingId is null
    @GetMapping("/pending-tracking")
    public List<OrderItems> getPendingTrackingOrders() {
        return orderItemsRepository.findByTrackingIdIsNull();
    }
    // Update status of a specific orderId
    @PutMapping("/update-status/{orderId}")
    public String updateOrderStatus(@PathVariable String orderId) {
        Optional<OrderItems> orderItemOptional = orderItemsRepository.findByOrderId(orderId);
        if (!orderItemOptional.isPresent()) {
            return "OrderItem not found";
        }

        OrderItems orderItem = orderItemOptional.get();
        orderItem.setStatus("Verified"); // update status
        orderItemsRepository.save(orderItem);

        return "Order status updated to Verified for orderId: " + orderId;
    }


    @GetMapping("/all-orders")
    public List<OrderItems> getAllOrders() {
        return orderItemsRepository.findAll(); // no join needed
    }







    @GetMapping("/summary")
    public List<OrderSummaryDTO> getOrderSummaries(
            @RequestParam(required = false) String customerId,
            @RequestParam(required = false) String status
    ) {
        return orderItemsRepository.findOrderSummaries(customerId, status);
    }

    @GetMapping("/processing-orders")
    public List<OrderSummaryDTO> getProcessingOrders() {
        return orderItemsRepository.findOrderSummaries(null, "Processing");
    }


    // Get order items by customer
    @GetMapping("/{customerId}")
    public List<OrderItems> getOrderItemsByCustomer(@PathVariable String customerId) {
        return orderItemsRepository.findByCustomerId(customerId);
    }
}


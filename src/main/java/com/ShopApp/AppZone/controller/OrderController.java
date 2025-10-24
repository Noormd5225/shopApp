package com.ShopApp.AppZone.controller;



import com.ShopApp.AppZone.model.Order;
import com.ShopApp.AppZone.model.OrderItems;
import com.ShopApp.AppZone.repository.OrderItemsRepository;
import com.ShopApp.AppZone.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;




    @GetMapping("/all-orders")
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(order -> new OrderDTO(
                        order.getOrderId(),
                        order.getOrderDate(),
                        order.getItemName(),
                        order.getQuantity(),
                        order.getPrice()
                ))
                .collect(Collectors.toList());
    }

    // DTO Class


    @Autowired
    private OrderItemsRepository orderItemsRepository;

    // ✅ API: Update order status
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



    // Save multiple orders
    @PostMapping("/create")
    public String createOrders(@RequestBody List<Order> orders) {
        for (Order order : orders) {
            order.setOrderDate(LocalDateTime.now());
        }
        orderRepository.saveAll(orders);
        return "Orders saved successfully";
    }

    // Get orders by customer
    @GetMapping("/{customerId}")
    public List<Order> getOrdersByCustomer(@PathVariable String customerId) {
        return orderRepository.findByCustomerId(customerId);
    }





    // ✅ 2. Filter by orderId
    @GetMapping("/by-order-id/{orderId}")
    public OrderResponse getOrdersByOrderId(@PathVariable String orderId) {
        List<Order> orders = orderRepository.findAll()
                .stream()
                .filter(o -> o.getOrderId().equals(orderId))
                .collect(Collectors.toList());
        return mapOrders(orders);
    }

    // ✅ 3. Filter by single date
    @GetMapping("/by-date")
    public OrderResponse getOrdersByDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {

        List<Order> orders = orderRepository.findAll()
                .stream()
                .filter(o -> o.getOrderDate().toLocalDate().equals(date.toLocalDate()))
                .collect(Collectors.toList());
        return mapOrders(orders);
    }

























    // ✅ 4. Filter between two dates
    @GetMapping("/between-dates")
    public OrderResponse getOrdersBetweenDates(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {

        List<Order> orders = orderRepository.findAll()
                .stream()
                .filter(o -> !o.getOrderDate().isBefore(startDate) && !o.getOrderDate().isAfter(endDate))
                .collect(Collectors.toList());
        return mapOrders(orders);
    }

    // ---------------- Utility Methods ----------------

    // Map orders to DTO and calculate grand total
    private OrderResponse mapOrders(List<Order> orders) {
        double grandTotal = orders.stream()
                .mapToDouble(o -> o.getPrice() * o.getQuantity())
                .sum();

        List<OrderDTO> dtoList = orders.stream()
                .map(o -> new OrderDTO(
                        o.getOrderId(),
                        o.getOrderDate(),
                        o.getItemName(),
                        o.getQuantity(),
                        o.getPrice()
                ))
                .collect(Collectors.toList());

        return new OrderResponse(dtoList, grandTotal);
    }

    // DTO for order
    public static class OrderDTO {
        private String orderId;
        private LocalDateTime orderDate;
        private String itemName;
        private int quantity;
        private double price;

        public OrderDTO(String orderId, LocalDateTime orderDate, String itemName, int quantity, double price) {
            this.orderId = orderId;
            this.orderDate = orderDate;
            this.itemName = itemName;
            this.quantity = quantity;
            this.price = price;
        }

        public String getOrderId() { return orderId; }
        public LocalDateTime getOrderDate() { return orderDate; }
        public String getItemName() { return itemName; }
        public int getQuantity() { return quantity; }
        public double getPrice() { return price; }
    }

    // Wrapper response including grand total
    public static class OrderResponse {
        private List<OrderDTO> orders;
        private double grandTotal;

        public OrderResponse(List<OrderDTO> orders, double grandTotal) {
            this.orders = orders;
            this.grandTotal = grandTotal;
        }

        public List<OrderDTO> getOrders() { return orders; }
        public double getGrandTotal() { return grandTotal; }
    }
}

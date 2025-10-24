package com.ShopApp.AppZone.repository;



import com.ShopApp.AppZone.model.Order;
import com.ShopApp.AppZone.model.OrderItems;
import com.ShopApp.AppZone.model.OrderSummaryDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderItemsRepository extends JpaRepository<OrderItems, Long> {
    List<OrderItems> findByCustomerId(String customerId);
    @Query("SELECT new com.ShopApp.AppZone.model.OrderSummaryDTO(" +
            "oi.orderId, oi.customerId, oi.orderDate, oi.amount, oi.status) " +
            "FROM OrderItems oi " +
            "WHERE (:customerId IS NULL OR oi.customerId = :customerId) " +
            "AND (:status IS NULL OR oi.status = :status)")
    List<OrderSummaryDTO> findOrderSummaries(
            @Param("customerId") String customerId,
            @Param("status") String status
    );




    List<OrderItems> findByTrackingIdIsNull();



    @Query(value = "SELECT oi.orderId FROM OrderItems oi ORDER BY oi.id DESC")
    List<String> findLastOrderId();

    @Query(
            value = "SELECT o.* FROM orders o " +
                    "JOIN order_items oi ON o.order_id = oi.order_id " +
                    "WHERE oi.customer_id = :customerId",
            nativeQuery = true
    )
    List<Order> findOrdersByCustomerUsingJoin(@Param("customerId") String customerId);
    Optional<OrderItems> findByOrderId(String orderId);
}

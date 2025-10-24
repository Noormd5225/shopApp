package com.ShopApp.AppZone.service;



import com.ShopApp.AppZone.model.Cart;
import com.ShopApp.AppZone.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    public Cart addToCart(Cart cart) {
        cart.setAddedDate(LocalDateTime.now()); // set current timestamp
        return cartRepository.save(cart);
    }

    public boolean deleteCartItemByProductId(String productId) {
        // Find the first cart item with this productId
        List<Cart> cartItems = cartRepository.findByProductId(productId);
        if (!cartItems.isEmpty()) {
            cartRepository.delete(cartItems.get(0)); // Delete only the first match
            return true;
        }
        return false;
    }

    public List<Cart> getCartByCustomer(String customerId) {
        return cartRepository.findByCustomerId(customerId);
    }
}

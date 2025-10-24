package com.ShopApp.AppZone.controller;


import com.ShopApp.AppZone.model.Cart;
import com.ShopApp.AppZone.model.VerifiedItem;
import com.ShopApp.AppZone.repository.VerifiedItemRepository;
import com.ShopApp.AppZone.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "*")
public class CartController {

    @Autowired
    private CartService cartService;

    // Upload image separately if needed
    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            String uploadDir = System.getProperty("user.dir") + "/uploads/";
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();

            String filename = UUID.randomUUID() + "_" + Objects.requireNonNull(file.getOriginalFilename());
            File dest = new File(uploadDir + filename);
            file.transferTo(dest);

            return ResponseEntity.ok("/uploads/" + filename);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Image upload failed");
        }
    }

    // Accept JSON payload from frontend
    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestBody Cart cart) {
        try {
            cart.setAddedDate(LocalDateTime.now());
            Cart saved = cartService.addToCart(cart);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error adding cart");
        }
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<?> getCart(@PathVariable String customerId) {
        try {
            return ResponseEntity.ok(cartService.getCartByCustomer(customerId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error fetching cart");
        }
    }



    // In CartController.java


    // ✅ New API: Get full cart with item images
    // In CartController.java


    @Autowired
    VerifiedItemRepository verifiedItemRepository;


    // ✅ New API: Get cart with verification item images (PROPER JOIN)
    // ✅ Fixed API: Get cart with verification item images (handles multiple results)
    @GetMapping("/with-images/{customerId}")
    public ResponseEntity<?> getCartWithVerificationImages(@PathVariable String customerId) {
        try {
            List<Cart> cartItems = cartService.getCartByCustomer(customerId);
            if (cartItems.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            List<Map<String, Object>> response = new ArrayList<>();
            for (Cart cart : cartItems) {
                Map<String, Object> map = new LinkedHashMap<>();

                // ✅ All Cart fields
                map.put("cartId", cart.getId());
                map.put("customerId", cart.getCustomerId());
                map.put("productId", cart.getProductId());
                map.put("itemName", cart.getItemName());
                map.put("itemImage", cart.getItemImage());
                map.put("quantity", cart.getQuantity());
                map.put("price", cart.getPrice());
                map.put("addedDate", cart.getAddedDate());

                // ✅ Join with VerifiedItem where cart.productId = verifiedItem.verification
                // Use findAll and filter instead of findByVerification to handle multiple results
                List<VerifiedItem> verifiedItems = verifiedItemRepository.findAll().stream()
                        .filter(v -> cart.getProductId().equals(v.getVerification()))
                        .toList();

                if (!verifiedItems.isEmpty()) {
                    // Take the first matching item
                    VerifiedItem verifiedItem = verifiedItems.get(0);
                    map.put("verificationItemImage", verifiedItem.getItemImage());

                    // Add other verification item details
                    map.put("verificationId", verifiedItem.getVerification());
                    map.put("sellingCost", verifiedItem.getSellingCost());
                    map.put("rate", verifiedItem.getRate());
                    map.put("discount1", verifiedItem.getDiscount1());
                    map.put("discount2", verifiedItem.getDiscount2());
                    map.put("discount3", verifiedItem.getDiscount3());
                    map.put("hotsale1", verifiedItem.getHotsale1());
                    map.put("hotsale2", verifiedItem.getHotsale2());
                    map.put("salesType", verifiedItem.getSalesType());
                    map.put("specification", verifiedItem.getSpecification());
                    map.put("status", verifiedItem.getStatus());
                    map.put("onlineDiscount", verifiedItem.getOnlineDiscount());
                } else {
                    map.put("verificationItemImage", null);
                }

                response.add(map);
            }

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error fetching cart with verification images: " + e.getMessage());
        }
    }


    // ✅ New API: Remove single cart item by productId
    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<?> removeCartItemByProductId(@PathVariable String productId) {
        try {
            // Delete single cart item by productId
            boolean deleted = cartService.deleteCartItemByProductId(productId);

            if (deleted) {
                return ResponseEntity.ok("Cart item removed successfully for productId: " + productId);
            } else {
                return ResponseEntity.status(404).body("No cart item found with productId: " + productId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error removing cart item: " + e.getMessage());
        }
    }




}

package com.ShopApp.AppZone.controller;


import com.ShopApp.AppZone.model.Product;
import com.ShopApp.AppZone.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    private ProductService service;

    // Original GET method
    @GetMapping
    public List<Product> getAll() {
        return service.getAll();
    }

    // GET products with category name (different URL to avoid conflict)
    @GetMapping("/with-category-name")
    public List<Map<String, String>> getAllWithCategoryName() {
        List<Product> products = service.getAll();
        List<Map<String, String>> result = new ArrayList<>();
        for (Product p : products) {
            Map<String, String> map = new HashMap<>();
            map.put("productId", p.getProductId());
            map.put("name", p.getName());
            map.put("categoryId", p.getCategoryId());
            map.put("categoryName", service.getCategoryNameById(p.getCategoryId()));
            result.add(map);
        }
        return result;
    }


    @GetMapping("/{id}")
    public Product getById(@PathVariable String id) {
        return service.getById(id);
    }

    @PostMapping
    public Product create(@RequestBody Product product) {
        return service.create(product);
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable String id, @RequestBody Product product) {
        return service.update(id, product);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        service.delete(id);
    }

    @GetMapping("/list")
    public List<Map<String, String>> getProductList() {
        List<Product> products = service.getAll();
        List<Map<String, String>> result = new ArrayList<>();
        for (Product prod : products) {
            Map<String, String> map = new HashMap<>();
            map.put("productId", prod.getProductId());
            map.put("name", prod.getName());
            result.add(map);
        }
        return result;
    }
}

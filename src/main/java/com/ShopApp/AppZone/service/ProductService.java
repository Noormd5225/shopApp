package com.ShopApp.AppZone.service;


import com.ShopApp.AppZone.model.Product;
import com.ShopApp.AppZone.model.ProductCategory;
import com.ShopApp.AppZone.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repo;

    public List<Product> getAll() {
        return repo.findAll();
    }

    public Product getById(String id) {
        return repo.findById(id).orElse(null);
    }

    public Product create(Product product) {
        String categoryId = product.getCategoryId();

        // Get existing products under this category
        List<Product> existing = repo.findByCategoryId(categoryId);

        int nextNum = existing.size() + 1;
        String newId = categoryId + "P" + String.format("%03d", nextNum);
        product.setProductId(newId);

        return repo.save(product);
    }

    public Product update(String id, Product product) {
        Product existing = getById(id);
        if (existing == null) return null;

        existing.setName(product.getName());
        existing.setCategoryId(product.getCategoryId());
        return repo.save(existing);
    }
    @Autowired
    private ProductCategoryService categoryService;

    public String getCategoryNameById(String categoryId) {
        if(categoryId == null) return null;
        return Optional.ofNullable(categoryService.getCategoryById(categoryId))
                .map(ProductCategory::getName)
                .orElse(null);
    }

    public void delete(String id) {
        repo.deleteById(id);
    }
}

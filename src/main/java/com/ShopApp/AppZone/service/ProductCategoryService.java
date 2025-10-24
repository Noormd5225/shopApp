package com.ShopApp.AppZone.service;


import com.ShopApp.AppZone.model.ProductCategory;
import com.ShopApp.AppZone.repository.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryService {

    @Autowired
    private ProductCategoryRepository repo;

    public List<ProductCategory> getAllCategories() {
        return repo.findAll();
    }

    public ProductCategory getCategoryById(String id) {
        return repo.findById(id).orElse(null);
    }

    public ProductCategory createCategory(ProductCategory category) {
        String lastId = repo.findLastCategoryId();
        if (lastId == null) category.setCategoryId("PC001");
        else {
            int num = Integer.parseInt(lastId.substring(2)) + 1;
            category.setCategoryId(String.format("PC%03d", num));
        }
        return repo.save(category);
    }

    public ProductCategory updateCategory(String id, ProductCategory category) {
        category.setCategoryId(id);
        return repo.save(category);
    }

    public void deleteCategory(String id) {
        repo.deleteById(id);
    }
}

        package com.ShopApp.AppZone.controller;

        import com.ShopApp.AppZone.model.ProductCategory;
        import com.ShopApp.AppZone.service.ProductCategoryService;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.web.bind.annotation.*;
        import org.springframework.web.multipart.MultipartFile;

        import java.io.File;
        import java.io.IOException;
        import java.util.*;

        @RestController
        @RequestMapping("/api/categories")
        @CrossOrigin(origins = "*")
        public class ProductCategoryController {

            @Autowired
            private ProductCategoryService service;

            @GetMapping
            public List<Map<String, String>> getAll() {
                List<ProductCategory> categories = service.getAllCategories();
                List<Map<String, String>> result = new ArrayList<>();
                for (ProductCategory cat : categories) {
                    Map<String, String> map = new HashMap<>();
                    map.put("categoryId", cat.getCategoryId());
                    map.put("name", cat.getName());
                    map.put("image", cat.getImage() != null ? cat.getImage() : ""); // just filename
                    result.add(map);
                }
                return result;
            }



            @GetMapping("/{id}")
            public ProductCategory getById(@PathVariable String id) {
                return service.getCategoryById(id);
            }

            @PostMapping
            public ProductCategory create(@RequestBody ProductCategory category) {
                return service.createCategory(category);
            }

            @PutMapping("/{id}")
            public ProductCategory update(@PathVariable String id, @RequestBody ProductCategory category) {
                return service.updateCategory(id, category);
            }

            @DeleteMapping("/{id}")
            public void delete(@PathVariable String id) {
                service.deleteCategory(id);
            }

            @GetMapping("/list")
            public List<Map<String, String>> getCategoryList() {
                List<ProductCategory> categories = service.getAllCategories();
                List<Map<String, String>> result = new ArrayList<>();
                for (ProductCategory cat : categories) {
                    Map<String, String> map = new HashMap<>();
                    map.put("categoryId", cat.getCategoryId());
                    map.put("name", cat.getName());
                    result.add(map);
                }
                return result;
            }

            // Corrected image upload
            @PostMapping("/upload")
            public Map<String, String> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
                String uploadDir = System.getProperty("user.dir") + "/uploads/";  // platform-independent
                File dir = new File(uploadDir);
                if (!dir.exists()) dir.mkdirs();

                String filename = UUID.randomUUID() + "_" + Objects.requireNonNull(file.getOriginalFilename());
                File dest = new File(uploadDir + filename);
                file.transferTo(dest);

                Map<String, String> result = new HashMap<>();
                result.put("filename", filename);
                result.put("path", "/uploads/" + filename); // optional path for frontend use
                return result;
            }
        }

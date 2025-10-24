package com.ShopApp.AppZone.controller;

import com.ShopApp.AppZone.model.Supplier;
import com.ShopApp.AppZone.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/suppliers")
@CrossOrigin(origins = "*")
public class SupplierController {
    @Autowired
    private SupplierService service;

    @GetMapping
    public List<Supplier> getAllSuppliers() {
        return service.getAllSuppliers();
    }

    @GetMapping("/{id}")
    public Supplier getSupplier(@PathVariable String id) {
        return service.getSupplierById(id);
    }

    @PostMapping
    public Supplier createSupplier(@RequestBody Supplier supplier) {
        return service.createSupplier(supplier);
    }

    @PutMapping("/{id}")
    public Supplier updateSupplier(@PathVariable String id, @RequestBody Supplier supplier) {
        return service.updateSupplier(id, supplier);
    }

    @GetMapping("/list")
    public List<Map<String, String>> getSupplierList() {
        List<Supplier> suppliers = service.getAllSuppliers();
        List<Map<String, String>> result = new ArrayList<>();
        for (Supplier s : suppliers) {
            Map<String, String> map = new HashMap<>();
            map.put("supplierId", s.getSupplierId());
            map.put("name", s.getSupplierName());
            result.add(map);
        }
        return result;
    }


    @DeleteMapping("/{id}")
    public String deleteSupplier(@PathVariable String id) {
        boolean deleted = service.deleteSupplier(id);
        return deleted ? "Deleted Successfully" : "Supplier Not Found";
    }
}


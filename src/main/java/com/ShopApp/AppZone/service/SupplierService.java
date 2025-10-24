package com.ShopApp.AppZone.service;

import com.ShopApp.AppZone.model.Supplier;
import com.ShopApp.AppZone.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService {
    @Autowired
    private SupplierRepository repository;

    // Get all suppliers
    public List<Supplier> getAllSuppliers() {
        return repository.findAll();
    }

    // Get supplier by ID
    public Supplier getSupplierById(String id) {
        return repository.findById(id).orElse(null);
    }

    // Create supplier with auto-generated ID
    public Supplier createSupplier(Supplier supplier) {
        String lastId = repository.findLastSupplierId();
        String newId;
        if (lastId == null) newId = "S001";
        else {
            int num = Integer.parseInt(lastId.substring(1)) + 1;
            newId = String.format("S%03d", num);
        }
        supplier.setSupplierId(newId);
        return repository.save(supplier);
    }

    // Update supplier
    public Supplier updateSupplier(String id, Supplier supplier) {
        Supplier existing = repository.findById(id).orElse(null);
        if (existing != null) {
            existing.setSupplierName(supplier.getSupplierName());
            existing.setOrganizationName(supplier.getOrganizationName());
            existing.setMobileNo(supplier.getMobileNo());
            existing.setGstNo(supplier.getGstNo());
            existing.setPanNo(supplier.getPanNo());
            existing.setDoorNo(supplier.getDoorNo());
            existing.setStreetName(supplier.getStreetName());
            existing.setTownVillage(supplier.getTownVillage());
            existing.setDistrict(supplier.getDistrict());
            existing.setState(supplier.getState());
            existing.setCountry(supplier.getCountry());
            existing.setPincode(supplier.getPincode());
            return repository.save(existing);
        }
        return null;
    }

    // Delete supplier
    public boolean deleteSupplier(String id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}

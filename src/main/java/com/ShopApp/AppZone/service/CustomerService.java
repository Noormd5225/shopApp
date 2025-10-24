package com.ShopApp.AppZone.service;



import com.ShopApp.AppZone.model.Customer;
import com.ShopApp.AppZone.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository repo;

    public List<Customer> getAllCustomers() {
        return repo.findAll();
    }

    public Customer getCustomerById(String id) {
        return repo.findById(id).orElse(null);
    }

    public Customer saveCustomer(Customer customer) {
        // Generate CustomerID
        if (customer.getCustomerId() == null || customer.getCustomerId().isEmpty()) {
            String lastId = repo.getLastCustomerId();
            if (lastId == null) lastId = "C000";
            int num = Integer.parseInt(lastId.substring(1)) + 1;
            customer.setCustomerId(String.format("C%03d", num));
        }
        return repo.save(customer);
    }



    // 1. Name and mobile by ID
    public Map<String, String> getNameAndMobileById(String id) {
        Customer c = repo.findById(id).orElse(null);
        if (c == null) return null;

        Map<String, String> map = new HashMap<>();
        map.put("customerName", c.getCustomerName());
        map.put("mobileNo", c.getMobileNo());
        return map;
    }

    // 2. Name, mobile, and full address by ID
    public Map<String, String> getNameMobileAddressById(String id) {
        Customer c = repo.findById(id).orElse(null);
        if (c == null) return null;

        Map<String, String> map = new HashMap<>();
        map.put("customerName", c.getCustomerName());
        map.put("mobileNo", c.getMobileNo());
        String address = c.getDoorNo() + ", " + c.getStreetName() + ", " +
                c.getTownVillage() + ", " + c.getDistrict() + ", " +
                c.getState() + ", " + c.getCountry() + " - " + c.getPincode();
        map.put("address", address);
        return map;
    }

    public Customer updateCustomerPassword(String id, String newPassword) {
        Customer customer = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
        customer.setPassword(newPassword);
        return repo.save(customer);
    }



    // 3. Update address by ID (already existing)
    public Customer updateCustomerAddress(String id, Map<String, String> addressMap) {
        Customer customer = repo.findById(id).orElse(null);
        if (customer != null) {
            if (addressMap.containsKey("doorNo")) customer.setDoorNo(addressMap.get("doorNo"));
            if (addressMap.containsKey("streetName")) customer.setStreetName(addressMap.get("streetName"));
            if (addressMap.containsKey("townVillage")) customer.setTownVillage(addressMap.get("townVillage"));
            if (addressMap.containsKey("district")) customer.setDistrict(addressMap.get("district"));
            if (addressMap.containsKey("state")) customer.setState(addressMap.get("state"));
            if (addressMap.containsKey("country")) customer.setCountry(addressMap.get("country"));
            if (addressMap.containsKey("pincode")) customer.setPincode(addressMap.get("pincode"));
            return repo.save(customer);
        }
        return null;
    }



    // 5. Get customer details by mobile number
    public Map<String, String> getCustomerByMobile(String mobileNo) {
        Customer c = repo.findByMobileNo(mobileNo);
        if (c == null) return null;

        Map<String, String> map = new HashMap<>();
        map.put("customerId", c.getCustomerId());
        map.put("customerName", c.getCustomerName());
        map.put("mobileNo", c.getMobileNo());
        return map;
    }


    public void deleteCustomer(String id) {
        repo.deleteById(id);
    }
}


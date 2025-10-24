package com.ShopApp.AppZone.controller;

import com.ShopApp.AppZone.model.Customer;
import com.ShopApp.AppZone.repository.CustomerRepository;
import com.ShopApp.AppZone.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService service;

    @GetMapping
    public List<Customer> getAll() {
        return service.getAllCustomers();
    }

    @GetMapping("/{id}")
    public Customer getById(@PathVariable String id) {
        return service.getCustomerById(id);
    }

    @PostMapping
    public Customer create(@RequestBody Customer customer) {
        return service.saveCustomer(customer);
    }

    @PutMapping("/{id}")
    public Customer update(@PathVariable String id, @RequestBody Customer customer) {
        customer.setCustomerId(id);
        return service.saveCustomer(customer);
    }

    @GetMapping("/{id}/basic")
    public Map<String, String> getCustomerNameAndMobile(@PathVariable String id) {
        return service.getNameAndMobileById(id);
    }

    // 2. Display customer name, mobile number, and full address by ID
    @GetMapping("/{id}/full-address")
    public Map<String, String> getCustomerWithAddress(@PathVariable String id) {
        return service.getNameMobileAddressById(id);
    }

    // 3. Update customer address by ID
    @PutMapping("/{id}/address")
    public Customer updateAddress(@PathVariable String id, @RequestBody Map<String, String> addressMap) {
        return service.updateCustomerAddress(id, addressMap);
    }


    // 5. Display customer name, id, and mobile number by mobileNo
    @GetMapping("/by-mobile/{mobileNo}")
    public Map<String, String> getCustomerByMobile(@PathVariable String mobileNo) {
        return service.getCustomerByMobile(mobileNo);
    }



    @Autowired
    private CustomerRepository repo;

    @PostMapping("/login")
    public Customer login(@RequestBody Map<String, String> loginMap) {
        String mobileNo = loginMap.get("mobileNo");
        String password = loginMap.get("password");

        // Fetch customer by mobile directly from repository
        Customer customer = repo.findByMobileNo(mobileNo);
        if (customer == null || !customer.getPassword().equals(password)) {
            throw new RuntimeException("Invalid mobile number or password");
        }

        return customer; // return full customer object
    }




    // 4. Update customer password by ID
    @PutMapping("/{id}/password")
    public Customer updatePassword(@PathVariable String id, @RequestBody Map<String, String> passwordMap) {
        String newPassword = passwordMap.get("password");
        return service.updateCustomerPassword(id, newPassword);
    }




    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        service.deleteCustomer(id);
    }
}

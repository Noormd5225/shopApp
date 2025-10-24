package com.ShopApp.AppZone.controller;


import com.ShopApp.AppZone.model.Employee;
import com.ShopApp.AppZone.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
@CrossOrigin(origins = "*")
public class EmployeeLoginController {

    @Autowired
    private EmployeeService service;

    @PostMapping
    public LoginResponse login(@RequestBody LoginRequest request) {
        Employee emp = service.getEmployeeById(request.getEmpId());
        if(emp != null && emp.getPassword().equals(request.getPassword())) {
            return new LoginResponse(emp.getEmpId(), emp.getName(), emp.getRole(), true);
        }
        return new LoginResponse(null, null, null, false);
    }

    // DTOs
    public static class LoginRequest {
        private String empId;
        private String password;

        public String getEmpId() { return empId; }
        public void setEmpId(String empId) { this.empId = empId; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    public static class LoginResponse {
        private String empId;
        private String name;
        private String role;
        private boolean success;

        public LoginResponse(String empId, String name, String role, boolean success) {
            this.empId = empId;
            this.name = name;
            this.role = role;
            this.success = success;
        }

        public String getEmpId() { return empId; }
        public String getName() { return name; }
        public String getRole() { return role; }
        public boolean isSuccess() { return success; }
    }
}


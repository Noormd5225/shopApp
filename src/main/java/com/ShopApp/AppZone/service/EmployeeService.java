package com.ShopApp.AppZone.service;


import com.ShopApp.AppZone.model.Employee;
import com.ShopApp.AppZone.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository repo;

    public List<Employee> getAllEmployees() {
        return repo.findAll();
    }

    public Employee getEmployeeById(String id) {
        return repo.findById(id).orElse(null);
    }

    public Employee createEmployee(Employee emp) {
        String lastId = repo.findLastEmpId();
        if (lastId == null) emp.setEmpId("E001");
        else {
            int num = Integer.parseInt(lastId.substring(1)) + 1;
            emp.setEmpId(String.format("E%03d", num));
        }
        return repo.save(emp);
    }

    public Employee updateEmployee(String id, Employee emp) {
        emp.setEmpId(id);
        return repo.save(emp);
    }

    public void deleteEmployee(String id) {
        repo.deleteById(id);
    }
}

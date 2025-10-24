package com.ShopApp.AppZone.repository;

import com.ShopApp.AppZone.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {

    @Query(value = "SELECT emp_id FROM employee ORDER BY emp_id DESC LIMIT 1", nativeQuery = true)
    String findLastEmpId();
}

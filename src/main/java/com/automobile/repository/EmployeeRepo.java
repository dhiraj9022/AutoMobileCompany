package com.automobile.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.automobile.entity.Employee;

public interface EmployeeRepo extends JpaRepository<Employee, Integer> {

}

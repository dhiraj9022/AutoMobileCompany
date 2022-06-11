package com.automobile.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.automobile.entity.Department;

public interface DepartmentRepo extends JpaRepository<Department, Integer> {

}

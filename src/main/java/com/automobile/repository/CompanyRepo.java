package com.automobile.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.automobile.entity.Company;

public interface CompanyRepo extends JpaRepository<Company, Integer> {

}

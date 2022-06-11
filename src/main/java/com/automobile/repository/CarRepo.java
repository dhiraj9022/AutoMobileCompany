package com.automobile.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.automobile.entity.Car;

public interface CarRepo extends JpaRepository<Car, Integer> {

}

package com.automobile.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.automobile.entity.Address;

public interface AddressRepo extends JpaRepository<Address, Integer> {

}

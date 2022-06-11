package com.automobile.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.automobile.entity.Address;
import com.automobile.pojos.AddressDto;
import com.automobile.service.AddressService;

@RestController
@RequestMapping("/api/address")
public class AddressController {

	private final AddressService addressService;

	public AddressController(AddressService addressService) {
		this.addressService = addressService;
	}

	@PostMapping
	public ResponseEntity<Address> saveAddress(@Valid @RequestBody AddressDto addressDto) {
		return ResponseEntity.ok(addressService.saveAddress(addressDto));
	}
	
	@GetMapping
	public ResponseEntity<List<Address>> getAddress(){
		return ResponseEntity.ok(addressService.fetchAddressList());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Address> getAddressById( @PathVariable(name = "id") int addressId){
		return ResponseEntity.ok(addressService.fetchAddressById(addressId));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Address> deleteAddressById( @PathVariable(name = "id") int addressId){
		addressService.deleteAddressById(addressId);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Address> updateAddress(@Valid @RequestBody AddressDto addressDto, @PathVariable(name = "id") int addressId) 
	{
		return ResponseEntity.ok(addressService.updateAdress(addressDto, addressId));
	}
}

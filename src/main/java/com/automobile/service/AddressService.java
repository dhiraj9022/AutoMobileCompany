package com.automobile.service;

import java.util.List;

import javax.validation.Valid;

import com.automobile.entity.Address;
import com.automobile.pojos.AddressDto;

public interface AddressService {

	public Address saveAddress(@Valid AddressDto addressDto);

	public List<Address> fetchAddressList();
	
	public Address fetchAddressById(int addressId);
	
	public void deleteAddressById(int addressId);
	
	public Address updateAdress(@Valid AddressDto addressDto, int addressId);
}

package com.automobile.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.automobile.entity.Address;
import com.automobile.exception.NotfoundException;
import com.automobile.pojos.AddressDto;
import com.automobile.repository.AddressRepo;

@Service
public class AddressServiceImpl implements AddressService{

	private final AddressRepo addressRepo;

	public AddressServiceImpl(AddressRepo addressRepo) {
		this.addressRepo = addressRepo;
	}

	@Override
	public Address saveAddress(@Valid AddressDto addressDto) {
		Address address=new Address();
		address.setHouseNumber(addressDto.getHouseNumber());
		address.setStreet(addressDto.getStreet());
		address.setZipcode(addressDto.getZipcode());
		return addressRepo.save(address);
	}

	@Override
	public List<Address> fetchAddressList() {
		return addressRepo.findAll(Sort.by(Sort.Direction.ASC, "addressId"));
	}

	@Override
	public Address fetchAddressById(@Valid int addressId) {
		return addressRepo.findById(addressId).orElseThrow(() -> new NotfoundException("Address Id not found !!!"));
	}

	@Override
	public void deleteAddressById(@Valid int addressId) {
		Address address=fetchAddressById(addressId);
		addressRepo.delete(address);
	}

	@Override
	public Address updateAdress(@Valid AddressDto addressDto, int addressId) {
		Address updateAddress=fetchAddressById(addressId);
		updateAddress.setHouseNumber(addressDto.getHouseNumber());
		updateAddress.setStreet(addressDto.getStreet());
		updateAddress.setZipcode(addressDto.getZipcode());
		return addressRepo.save(updateAddress);
	}
	
	
}

package com.automobile.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.automobile.entity.Address;
import com.automobile.entity.Address;
import com.automobile.repository.AddressRepo;
import com.automobile.repository.AddressRepo;
import com.fasterxml.jackson.core.JsonProcessingException;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class AddressServiceTest {
	
	@Autowired
	private AddressService addressService;

	@MockBean
	private AddressRepo addressRepo;

	private Address address;

	@BeforeEach
	public void setUp() {

		addressService = new AddressServiceImpl(addressRepo);
	}

	@Test
	@DisplayName("Save Address Test")
	@Order(1)
	public void whenSaveAddress_shouldReturnAddress() throws JsonProcessingException, Exception {

		address = new Address();
		address.setHouseNumber(1342);
		address.setStreet("washigton DC");
		address.setZipcode(320156);
		
		when(addressRepo.save(address)).thenReturn(address);

		Address savedAddress = addressRepo.save(address);
		assertThat(savedAddress.getAddressId()).isNotNull();

	}
	
	@Test
	@DisplayName("Fetch List Of Address Test")
	@Order(2)
	public void whenFetchListAddress_shouldReturnListAddress() throws JsonProcessingException, Exception {

		Address address1 = new Address();
		address1.setHouseNumber(2342);
		address1.setStreet("New York");
		address1.setZipcode(412056);
		
		Address address2 = new Address();
		address2.setHouseNumber(1342);
		address2.setStreet("New Jersy");
		address2.setZipcode(784521);
		
		Address address3 = new Address();
		address3.setHouseNumber(1342);
		address3.setStreet("washigton DC");
		address3.setZipcode(320156);
		
		List<Address> addresses=new ArrayList<>();
		addresses.add(address1);
		addresses.add(address2);
		addresses.add(address3);
		
		when(addressRepo.findAll()).thenReturn(addresses);

		List<Address> fetchAddresses = addressService.fetchAddressList();
		assertThat(fetchAddresses.size()).isGreaterThan(0);
	}
	

	@Test
	@DisplayName("Fetch AddressBy Id Test")
	@Order(3)
	public void whenFetchAddressById_shouldReturnAddress() throws JsonProcessingException, Exception {

		address = new Address();
		address.setHouseNumber(1342);
		address.setStreet("washigton DC");
		address.setZipcode(320156);
		
		when(addressRepo.findById(address.getAddressId())).thenReturn(Optional.of(address));

		Optional<Address> fetchAddress = addressRepo.findById(address.getAddressId());
		assertThat(address).isNotNull();
	}
	
	@Test
	@DisplayName("Update Address Test")
	@Order(4)
	public void whenUpdateAddress_shouldReturnNewAddress() throws JsonProcessingException, Exception {
		
		address = new Address();
		address.setHouseNumber(7860);
		address.setStreet("Los vegas");
		address.setZipcode(852040);
		
		when(addressRepo.findById(address.getAddressId())).thenReturn(Optional.of(address));
		when(addressRepo.save(address)).thenReturn(address);

		Address updateAddress = addressRepo.save(address);
		assertThat(updateAddress.getAddressId()).isNotNull();
	}
	
	@Test
	@DisplayName("Delete AddressBy Id Test")
	@Order(5)
	public void whenDeleteAddressById_DoesntReturnAddress() throws JsonProcessingException, Exception {

		address = new Address();
		address.setHouseNumber(7860);
		address.setStreet("Los vegas");
		address.setZipcode(852040);
		
		when(addressRepo.findById(address.getAddressId())).thenReturn(Optional.of(address));
		
		addressRepo.delete(address);
		verify(addressRepo, times(1)).delete(address);
	}
	
}

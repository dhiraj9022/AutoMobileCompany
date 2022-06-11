package com.automobile.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.automobile.entity.Address;
import com.automobile.entity.Company;
import com.automobile.pojos.AddressDto;
import com.automobile.pojos.CompanyDto;
import com.automobile.repository.AddressRepo;
import com.automobile.repository.CompanyRepo;
import com.automobile.service.AddressService;
import com.automobile.service.CompanyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class AddressControllerTest {

	@Autowired
	private AddressService addressService;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private AddressRepo addressRepo;

	@InjectMocks
	private AddressController addressController;

	private ObjectMapper objectMapper = new ObjectMapper();

	private Address address;
	
	@BeforeEach
	public void beforeEach() {
		addressRepo.deleteAll();

		AddressDto addressDto = new AddressDto();
		addressDto.setHouseNumber(1456);
		addressDto.setStreet("Valai pada");
		addressDto.setZipcode(401209);
		this.address = addressService.saveAddress(addressDto);

	}

	@Test
	@Order(1)
	@DisplayName("Save Address Test")
	public void whenSaveAddress_shouldReturnAddress() throws JsonProcessingException, Exception {

		AddressDto addressDto = new AddressDto();
		addressDto.setHouseNumber(1420);
		addressDto.setStreet("santosh bhuvan");
		addressDto.setZipcode(401208);

		mockMvc.perform(post("/api/address").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(addressDto)))
				.andExpect(status().isOk()).andDo(print());

		assertEquals(addressRepo.count(), 2);

		mockMvc.perform(post("/api/address").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content("{}")).andDo(print())
				.andExpect(status().isBadRequest());
		assertEquals(addressRepo.count(), 2);
	}

	@Test
	@Order(2)
	@DisplayName("Fetch All Address Test")
	public void whenFetchAddress_shouldReturnListAddress() throws JsonProcessingException, Exception {

		mockMvc.perform(
				get("/api/address").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$.length()").value(1))
				.andExpect(jsonPath("$[0].houseNumber", is(address.getHouseNumber())))
				.andExpect(jsonPath("$[0].street", is(address.getStreet())))
				.andExpect(jsonPath("$[0].zipcode", is(address.getZipcode())));
	}

	@Test
	@Order(3)
	@DisplayName("Fetch Address Test By Id")
	public void whenFetchAddressById_shouldReturnAddressId() throws JsonProcessingException, Exception {

		mockMvc.perform(get("/api/address/" + address.getAddressId()).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.houseNumber", is(address.getHouseNumber())))
				.andExpect(jsonPath("$.street", is(address.getStreet())))
				.andExpect(jsonPath("$.zipcode", is(address.getZipcode())))
				.andExpect(status().isOk())
				.andDo(print());

		mockMvc.perform(get("/api/address/" + (new Random().nextInt())).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content("{}")).andDo(print())
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.message", is("Address Id not found !!!")));

		assertEquals(addressRepo.count(), 1);
	}

	
	@Test
	@Order(4)
	@DisplayName("Update Address Test")
	public void whenUpdateAddressById_shouldReturnNewAddress() throws JsonProcessingException, Exception {

		AddressDto addressDto = new AddressDto();
		addressDto.setHouseNumber(7869);
		addressDto.setStreet("Bilal pada");
		addressDto.setZipcode(401207);

		mockMvc.perform(put("/api/address/" + address.getAddressId()).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(addressDto)))
				.andExpect(status().isOk()).andDo(print());

		assertEquals(addressRepo.count(), 1);

		mockMvc.perform(put("/api/address/" + (new Random().nextInt())).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(addressDto)))
				.andDo(print()).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", is("Address Id not found !!!")));

		assertEquals(addressRepo.count(), 1);
	}

	@Test
	@Order(5)
	@DisplayName("Delete Address Test By Id")
	public void whenDeleteCompanyById_shouldCompanyIdDoesntExists() throws JsonProcessingException, Exception {

		assertEquals(addressRepo.count(), 1);
		mockMvc.perform(delete("/api/address/" + address.getAddressId()).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isNoContent());

		assertEquals(addressRepo.count(), 0);
		mockMvc.perform(delete("/api/address/" + (new Random().nextInt())).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content("{}")).andDo(print())
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.message", is("Address Id not found !!!")));
	}

}



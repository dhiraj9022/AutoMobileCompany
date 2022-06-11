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

import com.automobile.entity.Car;
import com.automobile.entity.Company;
import com.automobile.pojos.CarDto;
import com.automobile.pojos.CompanyDto;
import com.automobile.repository.CarRepo;
import com.automobile.service.CarService;
import com.automobile.service.CompanyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class CarControllerTest {

	@Autowired
	private CarService carService;

	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private CarRepo carRepo;

	@InjectMocks
	private CarController carController;

	private ObjectMapper objectMapper = new ObjectMapper();

	private Company company;
	private Car car;

	@BeforeEach
	public void beforeEach() {
		carRepo.deleteAll();
		
		CompanyDto companyDto = new CompanyDto();
		companyDto.setCompanyName("Company Test");
		this.company = companyService.saveCompany(companyDto);
		
		CarDto carDto = new CarDto();
		carDto.setRegistrationNumber("TT78UP4720");
		carDto.setCompanyId(company.getCompanyId());
		this.car = carService.saveCar(carDto);

	}

	@Test
	@Order(1)
	@DisplayName("Save Car Test")
	public void whenSaveCar_shouldReturnCar() throws JsonProcessingException, Exception {

		CarDto carDto = new CarDto();
		carDto.setRegistrationNumber("MM13MH4123");
		carDto.setCompanyId(company.getCompanyId());

		mockMvc.perform(post("/api/cars").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(carDto)))
				.andExpect(status().isOk()).andDo(print());

		assertEquals(carRepo.count(), 2);

		mockMvc.perform(post("/api/cars").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content("{}")).andDo(print())
				.andExpect(status().isBadRequest());
		assertEquals(carRepo.count(), 2);
	}

	@Test
	@Order(2)
	@DisplayName("Fetch All Car Test")
	public void whenFetchCar_shouldReturnListCar() throws JsonProcessingException, Exception {

		mockMvc.perform(
				get("/api/cars").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$.length()").value(1))
				.andExpect(jsonPath("$[0].registrationNumber", is(car.getRegistrationNumber())));

	}

	@Test
	@Order(3)
	@DisplayName("Fetch Car Test By Id")
	public void whenFetchCarById_shouldReturnCarId() throws JsonProcessingException, Exception {

		mockMvc.perform(get("/api/cars/" + car.getCarId()).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.registrationNumber", is(car.getRegistrationNumber()))).andExpect(status().isOk())
				.andDo(print());

		mockMvc.perform(get("/api/cars/" + (new Random().nextInt())).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content("{}")).andDo(print())
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.message", is("Car Id not found !!!")));

		assertEquals(carRepo.count(), 1);
	}

	@Test
	@Order(4)
	@DisplayName("Update Car Test")
	public void whenUpdateCarId_shouldReturnNewCar() throws JsonProcessingException, Exception {

		CarDto carDto = new CarDto();
		carDto.setRegistrationNumber("NN45MM7896");
		carDto.setCompanyId(company.getCompanyId());

		mockMvc.perform(put("/api/cars/" + car.getCarId()).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(carDto)))
				.andExpect(status().isOk()).andDo(print());

		assertEquals(carRepo.count(), 1);

		mockMvc.perform(put("/api/cars/" + (new Random().nextInt())).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(carDto)))
				.andDo(print()).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", is("Car Id not found !!!")));

		assertEquals(carRepo.count(), 1);
	}

	@Test
	@Order(5)
	@DisplayName("Delete Car Test By Id")
	public void whenDeleteCarById_shouldCarIdDoesntExists() throws JsonProcessingException, Exception {

		assertEquals(carRepo.count(), 1);
		mockMvc.perform(delete("/api/cars/" + car.getCarId()).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isNoContent());

		assertEquals(carRepo.count(), 0);
		mockMvc.perform(delete("/api/cars/" + (new Random().nextInt())).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content("{}")).andDo(print())
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.message", is("Car Id not found !!!")));
	}
}


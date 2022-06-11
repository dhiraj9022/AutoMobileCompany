package com.automobile.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.automobile.entity.Company;
import com.automobile.pojos.CompanyDto;
import com.automobile.repository.CompanyRepo;
import com.automobile.service.CompanyService;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.core.JsonProcessingException;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class CompanyControllerTest {

	@Autowired
	private CompanyService companyService;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private CompanyRepo companyRepo;

	@InjectMocks
	private CompanyController companyController;

	private ObjectMapper objectMapper = new ObjectMapper();

	private Company company;

	@BeforeEach
	public void beforeEach() {
		companyRepo.deleteAll();

		CompanyDto companyDto = new CompanyDto();
		companyDto.setCompanyName("Company Test");
		this.company = companyService.saveCompany(companyDto);

	}

	@Test
	@Order(1)
	@DisplayName("Save Company Test")
	public void whenSaveCompany_shouldReturnCompany() throws JsonProcessingException, Exception {

		CompanyDto companyDto = new CompanyDto();
		companyDto.setCompanyName("New Company Test");

		mockMvc.perform(post("/api/companies").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(companyDto)))
				.andExpect(status().isOk()).andDo(print());

		assertEquals(companyRepo.count(), 2);

		mockMvc.perform(post("/api/companies").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content("{}")).andDo(print())
				.andExpect(status().isBadRequest());
		assertEquals(companyRepo.count(), 2);
	}

	@Test
	@Order(2)
	@DisplayName("Fetch All Company Test")
	public void whenFetchCompany_shouldReturnListCompany() throws JsonProcessingException, Exception {

		mockMvc.perform(
				get("/api/companies").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$.length()").value(1))
				.andExpect(jsonPath("$[0].companyName", is(company.getCompanyName())));

	}

	@Test
	@Order(3)
	@DisplayName("Fetch Company Test By Id")
	public void whenFetchCompanyById_shouldReturnCompanyId() throws JsonProcessingException, Exception {

		mockMvc.perform(get("/api/companies/" + company.getCompanyId()).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.companyName", is(company.getCompanyName()))).andExpect(status().isOk())
				.andDo(print());

		mockMvc.perform(get("/api/companies/" + (new Random().nextInt())).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content("{}")).andDo(print())
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.message", is(" Company Id not found !!!")));

		assertEquals(companyRepo.count(), 1);
	}

	@Test
	@Order(4)
	@DisplayName("Update Company Test")
	public void whenUpdateCompanyById_shouldReturnNewCompany() throws JsonProcessingException, Exception {

		CompanyDto companyDto = new CompanyDto();
		companyDto.setCompanyName("New Updated Company Test");

		mockMvc.perform(put("/api/companies/" + company.getCompanyId()).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(companyDto)))
				.andExpect(status().isOk()).andDo(print());

		assertEquals(companyRepo.count(), 1);

		mockMvc.perform(put("/api/companies/" + (new Random().nextInt())).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(companyDto)))
				.andDo(print()).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", is(" Company Id not found !!!")));

		assertEquals(companyRepo.count(), 1);
	}

	@Test
	@Order(5)
	@DisplayName("Delete Company Test By Id")
	public void whenDeleteCompanyById_shouldCompanyIdDoesntExists() throws JsonProcessingException, Exception {

		assertEquals(companyRepo.count(), 1);
		mockMvc.perform(delete("/api/companies/" + company.getCompanyId()).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isNoContent());

		assertEquals(companyRepo.count(), 0);
		mockMvc.perform(delete("/api/companies/" + (new Random().nextInt())).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content("{}")).andDo(print())
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.message", is(" Company Id not found !!!")));
	}

}

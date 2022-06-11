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


import com.automobile.entity.Company;
import com.automobile.entity.Department;
import com.automobile.pojos.CompanyDto;
import com.automobile.pojos.DepartmentDto;
import com.automobile.repository.DepartmentRepo;
import com.automobile.service.CompanyService;
import com.automobile.service.DepartmentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class DepartmentControllerTest {

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private DepartmentRepo departmentRepo;

	@InjectMocks
	private DepartmentController departmentController;

	private ObjectMapper objectMapper = new ObjectMapper();

	private Company company;
	private Department department;

	@BeforeEach
	public void beforeEach() {
		departmentRepo.deleteAll();
		
		CompanyDto companyDto = new CompanyDto();
		companyDto.setCompanyName("Company Test");
		this.company = companyService.saveCompany(companyDto);
		
		DepartmentDto departmentDto = new DepartmentDto();
		departmentDto.setDeptName("Billing Department");
		departmentDto.setCompanyId(company.getCompanyId());
		this.department = departmentService.saveDepartment(departmentDto);

	}

	@Test
	@Order(1)
	@DisplayName("Save Department Test")
	public void whenSaveDepartment_shouldReturnDepartment() throws JsonProcessingException, Exception {

		DepartmentDto departmentDto = new DepartmentDto();
		departmentDto.setDeptName("Accountant Department");
		departmentDto.setCompanyId(company.getCompanyId());

		mockMvc.perform(post("/api/departments").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(departmentDto)))
				.andExpect(status().isOk()).andDo(print());

		assertEquals(departmentRepo.count(), 2);

		mockMvc.perform(post("/api/departments").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content("{}")).andDo(print())
				.andExpect(status().isBadRequest());
		assertEquals(departmentRepo.count(), 2);
	}

	@Test
	@Order(2)
	@DisplayName("Fetch All Department Test")
	public void whenFetchDepartment_shouldReturnListDepartment() throws JsonProcessingException, Exception {

		mockMvc.perform(
				get("/api/departments").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$.length()").value(1))
				.andExpect(jsonPath("$[0].deptName", is(department.getDeptName())));

	}

	@Test
	@Order(3)
	@DisplayName("Fetch Department Test By Id")
	public void whenFetchDepartmentById_shouldReturnDepartmentId() throws JsonProcessingException, Exception {

		mockMvc.perform(get("/api/departments/" + department.getDeptId()).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.deptId", is(department.getDeptId()))).andExpect(status().isOk())
				.andDo(print());

		mockMvc.perform(get("/api/departments/" + (new Random().nextInt())).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content("{}")).andDo(print())
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.message", is("Dept Id not found !!!")));

		assertEquals(departmentRepo.count(), 1);
	}

	@Test
	@Order(4)
	@DisplayName("Update Department Test")
	public void whenUpdateDepartmentId_shouldReturnNewDepartment() throws JsonProcessingException, Exception {

		
		DepartmentDto departmentDto = new DepartmentDto();
		departmentDto.setDeptName("New Customer Service Department");
		departmentDto.setCompanyId(company.getCompanyId());

		mockMvc.perform(put("/api/departments/" + department.getDeptId()).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(departmentDto)))
				.andExpect(status().isOk()).andDo(print());

		assertEquals(departmentRepo.count(), 1);

		mockMvc.perform(put("/api/departments/" + (new Random().nextInt())).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(departmentDto)))
				.andDo(print()).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", is("Dept Id not found !!!")));

		assertEquals(departmentRepo.count(), 1);
	}

	@Test
	@Order(5)
	@DisplayName("Delete Department Test By Id")
	public void whenDeleteDepartmentById_shouldDepartmentIdDoesntExists() throws JsonProcessingException, Exception {

		assertEquals(departmentRepo.count(), 1);
		mockMvc.perform(delete("/api/departments/" + department.getDeptId()).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isNoContent());

		assertEquals(departmentRepo.count(), 0);
		mockMvc.perform(delete("/api/departments/" + (new Random().nextInt())).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content("{}")).andDo(print())
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.message", is("Dept Id not found !!!")));
	}
}


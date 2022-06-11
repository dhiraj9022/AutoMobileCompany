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
import com.automobile.entity.Department;
import com.automobile.entity.Employee;
import com.automobile.pojos.AddressDto;
import com.automobile.pojos.CompanyDto;
import com.automobile.pojos.DepartmentDto;
import com.automobile.pojos.EmployeeDto;
import com.automobile.repository.DepartmentRepo;
import com.automobile.repository.EmployeeRepo;
import com.automobile.service.AddressService;
import com.automobile.service.CompanyService;
import com.automobile.service.DepartmentService;
import com.automobile.service.EmployeeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class EmployeeControllerTest {

	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private AddressService addressService;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private EmployeeRepo employeeRepo;

	@InjectMocks
	private EmployeeController employeeController;

	private ObjectMapper objectMapper = new ObjectMapper();

	private Company company;
	private Employee employee;
	private Department department;
	private Address address;

	@BeforeEach
	public void beforeEach() {
		employeeRepo.deleteAll();
		
		CompanyDto companyDto = new CompanyDto();
		companyDto.setCompanyName("Company Test");
		this.company = companyService.saveCompany(companyDto);
		
		DepartmentDto departmentDto = new DepartmentDto();
		departmentDto.setDeptName("Fake Department");
		departmentDto.setCompanyId(company.getCompanyId());
		this.department = departmentService.saveDepartment(departmentDto);
		
		AddressDto addressDto = new AddressDto();
		addressDto.setHouseNumber(1430);
		addressDto.setStreet("Gaurai pada");
		addressDto.setZipcode(401206);
		this.address=addressService.saveAddress(addressDto);
		
		EmployeeDto employeeDto = new EmployeeDto();
		employeeDto.setEmpName("Dhiraj");
		employeeDto.setEmpSurname("Vish");
		employeeDto.setDeptId(department.getDeptId());
		employeeDto.setAddressId(address.getAddressId());
		this.employee = employeeService.saveEmployee(employeeDto);

	}

	@Test
	@Order(1)
	@DisplayName("Save Employee Test")
	public void whenSaveEmployee_shouldReturnEmployee() throws JsonProcessingException, Exception {

		EmployeeDto employeeDto = new EmployeeDto();
		employeeDto.setEmpName("Amit");
		employeeDto.setEmpSurname("Kesarwani");
		employeeDto.setDeptId(department.getDeptId());
		employeeDto.setAddressId(address.getAddressId());

		mockMvc.perform(post("/api/employees").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(employeeDto)))
				.andExpect(status().isOk()).andDo(print());

		assertEquals(employeeRepo.count(), 2);

		mockMvc.perform(post("/api/employees").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content("{}")).andDo(print())
				.andExpect(status().isBadRequest());
		assertEquals(employeeRepo.count(), 2);
	}

	@Test
	@Order(2)
	@DisplayName("Fetch All Employee Test")
	public void whenFetchEmployee_shouldReturnListEmployee() throws JsonProcessingException, Exception {

		mockMvc.perform(
				get("/api/employees").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$.length()").value(1))
				.andExpect(jsonPath("$[0].empName", is(employee.getEmpName())))
				.andExpect(jsonPath("$[0].empSurname", is(employee.getEmpSurname())));
	}

	@Test
	@Order(3)
	@DisplayName("Fetch Employee Test By Id")
	public void whenFetchEmployeeById_shouldReturnEmployeeId() throws JsonProcessingException, Exception {

		mockMvc.perform(get("/api/employees/" + employee.getEmpId()).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.empId", is(employee.getEmpId()))).andExpect(status().isOk())
				.andDo(print());

		mockMvc.perform(get("/api/employees/" + (new Random().nextInt())).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content("{}")).andDo(print())
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.message", is("Employee Id not found !!!")));

		assertEquals(employeeRepo.count(), 1);
	}

	@Test
	@Order(4)
	@DisplayName("Update Employee Test")
	public void whenUpdateEmployeeId_shouldReturnNewEmployee() throws JsonProcessingException, Exception {

		EmployeeDto employeeDto = new EmployeeDto();
		employeeDto.setEmpName("ankit");
		employeeDto.setEmpSurname("upadhayay");
		employeeDto.setDeptId(department.getDeptId());
		employeeDto.setAddressId(address.getAddressId());
		
		mockMvc.perform(put("/api/employees/" + employee.getEmpId()).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(employeeDto)))
				.andExpect(status().isOk()).andDo(print());

		assertEquals(employeeRepo.count(), 1);

		mockMvc.perform(put("/api/employees/" + (new Random().nextInt())).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(employeeDto)))
				.andDo(print()).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", is("Employee Id not found !!!")));

		assertEquals(employeeRepo.count(), 1);
	}

	@Test
	@Order(5)
	@DisplayName("Delete Employee Test By Id")
	public void whenDeleteEmployeeById_shouldEmployeeIdDoesntExists() throws JsonProcessingException, Exception {

		assertEquals(employeeRepo.count(), 1);
		mockMvc.perform(delete("/api/employees/" + employee.getEmpId()).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isNoContent());

		assertEquals(employeeRepo.count(), 0);
		mockMvc.perform(delete("/api/employees/" + (new Random().nextInt())).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content("{}")).andDo(print())
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.message", is("Employee Id not found !!!")));
	}
}



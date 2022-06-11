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
import com.automobile.entity.Company;
import com.automobile.entity.Department;
import com.automobile.entity.Employee;
import com.automobile.pojos.EmployeeDto;
import com.automobile.repository.AddressRepo;
import com.automobile.repository.DepartmentRepo;
import com.automobile.repository.EmployeeRepo;
import com.fasterxml.jackson.core.JsonProcessingException;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class EmployeeServiceTest {
	
	@Autowired
	private AddressService AddressService;

	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private EmployeeService employeeService;

	@MockBean
	private EmployeeRepo employeeRepo;
	
	@MockBean
	private AddressRepo AddressRepo;

	@MockBean
	private DepartmentRepo departmentRepo;
	
	private Company company;
	private Employee employee;
	private Address address;
	private Department department;

	@BeforeEach
	public void setUp() {

		employeeService=new EmployeeServiceImpl(employeeRepo);
		AddressService = new AddressServiceImpl(AddressRepo);
		departmentService = new DepartmentServiceImpl(departmentRepo);
	}

	@Test
	@DisplayName("Save Employee Test")
	@Order(1)
	public void whenSaveEmployee_shouldReturnEmployee() throws JsonProcessingException, Exception {

		address = new Address();
		address.setHouseNumber(1342);
		address.setStreet("washigton DC");
		address.setZipcode(320156);
	
		department=new Department();
		department.setDeptName("Department Abc");
		department.setCompany(company);
		
		Employee employee = new Employee();
		employee.setEmpId(1);
		employee.setEmpName("Dhiraj");
		employee.setEmpSurname("Vish");
		employee.setAddress(address);
		employee.setDepartment(department);
		
		when(employeeRepo.save(employee)).thenReturn(employee);

		Employee savedEmployee = employeeRepo.save(employee);
		assertThat(savedEmployee).isNotNull();
		System.out.println(savedEmployee);
	}
	
	@Test
	@DisplayName("Fetch List Of Employee Test")
	@Order(2)
	public void whenFetchListEmployee_shouldReturnListEmployee() throws JsonProcessingException, Exception {

		Address address1 = new Address();
		address1.setHouseNumber(2342);
		address1.setStreet("New York");
		address1.setZipcode(412056);
		
		Address address2 = new Address();
		address2.setHouseNumber(1342);
		address2.setStreet("New Jersy");
		address2.setZipcode(784521);
		
		Department department1=new Department();
		department1.setDeptName("Design department");
		
		Department department2=new Department();
		department2.setDeptName("Manufacture department");
		
		Employee employee1 = new Employee();
		employee1.setEmpName("Amit");
		employee1.setEmpSurname("kesarwani");
		employee1.setAddress(address1);
		employee1.setDepartment(department1);
		
		Employee employee2 = new Employee();
		employee2.setEmpName("Dhiraj");
		employee2.setEmpSurname("Vish");
		employee2.setAddress(address2);
		employee2.setDepartment(department2);
		
		List<Employee> employees=new ArrayList<>();
		employees.add(employee1);
		employees.add(employee2);
		
		when(employeeRepo.findAll()).thenReturn(employees);

		List<Employee> fetchEmployees = employeeService.fetchEmployeeList();
		assertThat(fetchEmployees.size()).isGreaterThan(0);
		System.out.println(fetchEmployees);
	}

	@Test
	@DisplayName("Fetch EmployeeBy Id Test")
	@Order(3)
	public void whenFetchEmployeeById_shouldReturnEmployee() throws JsonProcessingException, Exception {

		address = new Address();
		address.setHouseNumber(1342);
		address.setStreet("washigton DC");
		address.setZipcode(320156);
	
		department=new Department();
		department.setDeptName("Department Abc");
		department.setCompany(company);
		
		Employee employee = new Employee();
		employee.setEmpId(1);
		employee.setEmpName("Dhiraj");
		employee.setEmpSurname("Vish");
		employee.setAddress(address);
		employee.setDepartment(department);
		
		when(employeeRepo.findById(employee.getEmpId())).thenReturn(Optional.of(employee));

		Optional<Employee> fetchEmployee = employeeRepo.findById(employee.getEmpId());
		assertThat(fetchEmployee).isNotNull();
	}
	
	@Test
	@DisplayName("Update Employee Test")
	@Order(4)
	public void whenUpdateEmployee_shouldReturnNewEmployee() throws JsonProcessingException, Exception {

		address = new Address();
		address.setHouseNumber(1342);
		address.setStreet("washigton DC");
		address.setZipcode(320156);
	
		department=new Department();
		department.setDeptName("Department Abc");
		department.setCompany(company);
		
		Employee employee = new Employee();
		employee.setEmpId(1);
		employee.setEmpName("Dhiraj");
		employee.setEmpSurname("Vish");
		employee.setAddress(address);
		employee.setDepartment(department);
		
		when(employeeRepo.findById(employee.getEmpId())).thenReturn(Optional.of(employee));
		when(employeeRepo.save(employee)).thenReturn(employee);

		Employee updateEmployee = employeeRepo.save(employee);
		assertThat(updateEmployee.getEmpId()).isNotNull();
	}
	
	@Test
	@DisplayName("Delete EmployeeBy Id Test")
	@Order(5)
	public void whenDeleteEmployeeById_DoesntReturnEmployee() throws JsonProcessingException, Exception {

		address = new Address();
		address.setHouseNumber(1342);
		address.setStreet("washigton DC");
		address.setZipcode(320156);
	
		department=new Department();
		department.setDeptName("Department Abc");
		department.setCompany(company);
		
		Employee employee = new Employee();
		employee.setEmpId(1);
		employee.setEmpName("Dhiraj");
		employee.setEmpSurname("Vish");
		employee.setAddress(address);
		employee.setDepartment(department);
		
		when(employeeRepo.findById(employee.getEmpId())).thenReturn(Optional.of(employee));
		
		employeeRepo.delete(employee);
		verify(employeeRepo, times(1)).delete(employee);
	}
	

}
package com.automobile.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.startsWith;
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
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DeadlockLoserDataAccessException;

import com.automobile.entity.Company;
import com.automobile.entity.Department;
import com.automobile.repository.CompanyRepo;
import com.automobile.repository.DepartmentRepo;
import com.fasterxml.jackson.core.JsonProcessingException;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class DepartmentServiceTest {
	
	@Autowired
	private CompanyService companyService;

	@MockBean
	private CompanyRepo companyRepo;

	@MockBean
	private DepartmentRepo departmentRepo;
	
	private DepartmentService departmentService;

	private Company company;
	
	private Department department;

	@BeforeEach
	public void setUp() {

		companyService = new CompanyServiceImpl(companyRepo);
		departmentService = new DepartmentServiceImpl(departmentRepo);
	}

	public void departmentCompany() {
		company = new Company();
		company.setCompanyId(1);
		company.setCompanyName("Bently company");
	
		department=new Department();
		department.setDeptName("Department Abc");
		department.setCompany(company);
	}
	
	@Test
	@DisplayName("Save department Test")
	@Order(1)
	public void whenSaveDepartment_shouldReturnDepartment() throws JsonProcessingException, Exception {

		departmentCompany();
		
		when(departmentRepo.save(department)).thenReturn(department);

		Department savedDepartment = departmentRepo.save(department);
		assertThat(savedDepartment.getDeptId()).isNotNull();
		System.out.println(savedDepartment);
	}
	
	@Test
	@DisplayName("Fetch List Of Department Test")
	@Order(2)
	public void whenFetchListDepartment_shouldReturnListDepartment() throws JsonProcessingException, Exception {

		Company company1 = new Company();
		company1.setCompanyId(1);
		company1.setCompanyName("Juagar company");
		
		Company company2 = new Company();
		company1.setCompanyId(2);
		company2.setCompanyName("Maruti company");
		
		Department department1=new Department();
		department1.setCompany(company1);
		department1.setDeptName("Design department");
		
		Department department2=new Department();
		department2.setCompany(company2);
		department2.setDeptName("Manufacture department");
		
		List<Department> departments=new ArrayList<>();
		departments.add(department1);
		departments.add(department2);
		
		
		when(departmentRepo.findAll()).thenReturn(departments);

		List<Department> fetchDepartments = departmentService.fetchDepartmentList();
		assertThat(fetchDepartments.size()).isGreaterThan(0);
		System.out.println(fetchDepartments);
	}

	@Test
	@DisplayName("Fetch DepartmentBy Id Test")
	@Order(3)
	public void whenFetchDepartmentById_shouldReturnDepartment() throws JsonProcessingException, Exception {

		departmentCompany();
		
		when(departmentRepo.findById(department.getDeptId())).thenReturn(Optional.of(department));

		Optional<Department> fetchDepartment = departmentRepo.findById(department.getDeptId());
		assertThat(fetchDepartment).isNotNull();
	}
	
	@Test
	@DisplayName("Update Department Test")
	@Order(4)
	public void whenUpdateDepartment_shouldReturnNewDepartment() throws JsonProcessingException, Exception {

		departmentCompany();
		
		when(departmentRepo.findById(department.getDeptId())).thenReturn(Optional.of(department));
		when(departmentRepo.save(department)).thenReturn(department);

		Department updateDepartment = departmentRepo.save(department);
		assertThat(updateDepartment.getDeptId()).isNotNull();
	}
	
	@Test
	@DisplayName("Delete DepartmentBy Id Test")
	@Order(5)
	public void whenDeleteDepartmentById_DoesntReturnDepartment() throws JsonProcessingException, Exception {

		departmentCompany();
		
		when(departmentRepo.findById(department.getDeptId())).thenReturn(Optional.of(department));
		
		departmentRepo.delete(department);
		verify(departmentRepo, times(1)).delete(department);
	}
	
}
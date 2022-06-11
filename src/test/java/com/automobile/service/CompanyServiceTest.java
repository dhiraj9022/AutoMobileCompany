package com.automobile.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;

import java.nio.charset.CoderMalfunctionError;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.catalina.authenticator.SavedRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.mockito.ArgumentMatchers;
import org.postgresql.shaded.com.ongres.scram.common.message.ServerFinalMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.automobile.entity.Company;
import com.automobile.pojos.CompanyDto;
import com.automobile.repository.CompanyRepo;
import com.fasterxml.jackson.core.JsonProcessingException;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class CompanyServiceTest {

	@Autowired
	private CompanyService companyService;

	@MockBean
	private CompanyRepo companyRepo;

	private Company company;

	@BeforeEach
	public void setUp() {

		companyService = new CompanyServiceImpl(companyRepo);
	}

	@Test
	@DisplayName("Save Company Test")
	@Order(1)
	public void whenSaveCompany_shouldReturnCompany() throws JsonProcessingException, Exception {

		company = new Company();
		company.setCompanyName("Bently company");
		when(companyRepo.save(company)).thenReturn(company);

		Company savedCompany = companyRepo.save(company);
		assertThat(savedCompany.getCompanyId()).isNotNull();

	}
	
	@Test
	@DisplayName("Fetch List Of Company Test")
	@Order(2)
	public void whenFetchListCompany_shouldReturnListCompany() throws JsonProcessingException, Exception {

		Company company1 = new Company();
		company1.setCompanyName("Juagar company");
		
		Company company2 = new Company();
		company2.setCompanyName("Maruti company");
		
		Company company3 = new Company();
		company3.setCompanyName("Ford company");
		
		List<Company> companies=new ArrayList<>();
		companies.add(company1);
		companies.add(company2);
		companies.add(company3);
		
		when(companyRepo.findAll()).thenReturn(companies);

		List<Company> fetchCompanies = companyService.fetchCompanyList();
		assertThat(fetchCompanies.size()).isGreaterThan(0);
	}
	
	@Test
	@DisplayName("Fetch CompanyBy Id Test")
	@Order(3)
	public void whenFetchCompanyById_shouldReturnCompany() throws JsonProcessingException, Exception {

		Company company = new Company();
		company.setCompanyId(1);
		company.setCompanyName("Mahindra company");
		
		when(companyRepo.findById(company.getCompanyId())).thenReturn(Optional.of(company));

		Optional<Company> comapny = companyRepo.findById(company.getCompanyId());
		assertThat(comapny).isNotNull();
	}
	
	@Test
	@DisplayName("Update Company Test")
	@Order(4)
	public void whenUpdateCompany_shouldReturnNewCompany() throws JsonProcessingException, Exception {

		company = new Company();
		company.setCompanyId(1);
		company.setCompanyName("Volkswagen company");
		
		when(companyRepo.findById(company.getCompanyId())).thenReturn(Optional.of(company));
		when(companyRepo.save(company)).thenReturn(company);

		Company updateCompany = companyRepo.save(company);
		assertThat(updateCompany.getCompanyId()).isNotNull();
	}
	
	@Test
	@DisplayName("Delete CompanyBy Id Test")
	@Order(5)
	public void whenDeleteCompanyById_DoesntReturnCompany() throws JsonProcessingException, Exception {

		Company company = new Company();
		company.setCompanyId(1);
		company.setCompanyName("Honda company");
		
		when(companyRepo.findById(company.getCompanyId())).thenReturn(Optional.of(company));
		
		companyRepo.delete(company);
		verify(companyRepo, times(1)).delete(company);
	}
	
}

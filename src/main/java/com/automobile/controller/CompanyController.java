package com.automobile.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.automobile.entity.Company;
import com.automobile.pojos.CompanyDto;
import com.automobile.service.CompanyService;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

	private final CompanyService companyService;

	public CompanyController(CompanyService companyService) {
		this.companyService = companyService;
	}
	
	@PostMapping
	public ResponseEntity<Company> saveCompany(@Valid @RequestBody CompanyDto companyDto)  {
		return ResponseEntity.ok(companyService.saveCompany(companyDto));
	}
	
	@GetMapping
	public ResponseEntity<List<Company>> getCompanies(){
		return ResponseEntity.ok(companyService.fetchCompanyList());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Company> getCompanyById(@PathVariable(name = "id") int companyId){
		return ResponseEntity.ok(companyService.fetchCompanyById(companyId));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Company> deleteCompanyById(@PathVariable(name = "id") int companyId){
		companyService.deleteCompanyById(companyId);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Company> updateCompany(@Valid @RequestBody CompanyDto companyDto, @PathVariable(name = "id") int companyId)
	{
		return ResponseEntity.ok(companyService.updateCompany(companyDto, companyId));
	}
}

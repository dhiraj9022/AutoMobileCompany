package com.automobile.service;

import java.util.List;

import javax.validation.Valid;

import com.automobile.entity.Company;
import com.automobile.pojos.CompanyDto;

public interface CompanyService {

	 public Company saveCompany(@Valid CompanyDto companyDto);
	 
	 public List<Company> fetchCompanyList();
	 
	 public Company fetchCompanyById(int companyId);
	 
	 public void deleteCompanyById(int companyId);
	 
	 public Company updateCompany(@Valid CompanyDto companyDto, int companyId);
}

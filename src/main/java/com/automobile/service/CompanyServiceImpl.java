package com.automobile.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.automobile.entity.Company;
import com.automobile.exception.NotfoundException;
import com.automobile.pojos.CompanyDto;
import com.automobile.repository.CompanyRepo;

@Service
public class CompanyServiceImpl implements CompanyService {

	private CompanyRepo companyRepo;

	public CompanyServiceImpl(CompanyRepo companyRepo) {
		this.companyRepo = companyRepo;
	}

	@Override
	public Company saveCompany(@Valid CompanyDto companyDto) {
		Company company=new Company();
		company.setCompanyName(companyDto.getCompanyName());
		return companyRepo.save(company);
	}

	@Override
	public List<Company> fetchCompanyList() {
		return companyRepo.findAll(Sort.by(Sort.Direction.ASC, "companyId"));
	}

	@Override
	public Company fetchCompanyById(@Valid int companyId) {
		
		return companyRepo.findById(companyId).orElseThrow(() -> new NotfoundException(" Company Id not found !!!"));
	}

	@Override
	public void deleteCompanyById(@Valid int companyId) {
		Company company=fetchCompanyById(companyId);
	    companyRepo.delete(company);
	}

	@Override
	public Company updateCompany(@Valid CompanyDto companyDto, int companyId) {
		Company updateCompany=fetchCompanyById(companyId);
		updateCompany.setCompanyName(companyDto.getCompanyName());
		return companyRepo.save(updateCompany);
	}

}

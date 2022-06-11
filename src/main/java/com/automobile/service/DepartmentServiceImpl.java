package com.automobile.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.automobile.entity.Department;
import com.automobile.exception.NotfoundException;
import com.automobile.pojos.DepartmentDto;
import com.automobile.repository.DepartmentRepo;

@Service
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	private CompanyService companyService;
	
	private final DepartmentRepo departmentRepo;
	
	public DepartmentServiceImpl(DepartmentRepo departmentRepo) {
		this.departmentRepo = departmentRepo;
	}

	@Override
	public Department saveDepartment(@Valid DepartmentDto departmentDto) {
		Department department=new Department();
		department.setDeptName(departmentDto.getDeptName());
		department.setCompany(companyService.fetchCompanyById(departmentDto.getCompanyId()));
		return departmentRepo.save(department);
	}

	@Override
	public List<Department> fetchDepartmentList() {
		return departmentRepo.findAll(Sort.by(Sort.Direction.ASC, "deptId"));
	}

	@Override
	public Department fetchDepartmentById(@Valid int deptId) {
		return departmentRepo.findById(deptId).orElseThrow(() -> new NotfoundException("Dept Id not found !!!"));
	}

	@Override
	public void deleteDepartmentById(@Valid int deptId) {
		Department department=fetchDepartmentById(deptId);
		departmentRepo.delete(department);
	}

	@Override
	public Department updateDepartment(@Valid DepartmentDto departmentDto, int deptId) {
		Department updateDept =fetchDepartmentById(deptId);
		updateDept.setDeptName(departmentDto.getDeptName());
		updateDept.setCompany(companyService.fetchCompanyById(departmentDto.getCompanyId()));
		return departmentRepo.save(updateDept);
	}

}

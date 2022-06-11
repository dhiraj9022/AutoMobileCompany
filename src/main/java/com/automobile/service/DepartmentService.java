package com.automobile.service;

import java.util.List;

import javax.validation.Valid;

import com.automobile.entity.Department;
import com.automobile.pojos.DepartmentDto;

public interface DepartmentService {
	public Department saveDepartment(@Valid DepartmentDto departmentDto);

	public List<Department> fetchDepartmentList();
	
	public Department fetchDepartmentById(int deptId);
	
	public void deleteDepartmentById(int deptId);
	
	public Department updateDepartment(@Valid DepartmentDto departmentDto, int deptId);
}

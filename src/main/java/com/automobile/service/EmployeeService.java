package com.automobile.service;

import java.util.List;

import javax.validation.Valid;

import com.automobile.entity.Employee;
import com.automobile.pojos.EmployeeDto;

public interface EmployeeService {
	
	public Employee saveEmployee(@Valid EmployeeDto employeeDto);

	public List<Employee> fetchEmployeeList();
	
	public Employee fetchEMployeeById(int empId);
	
	public void deleteEmployeeyId(int empId);
	
	public Employee updateEmployee(@Valid EmployeeDto employeeDto, int empId);
}

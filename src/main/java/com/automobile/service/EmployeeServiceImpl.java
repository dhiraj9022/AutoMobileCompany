package com.automobile.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.automobile.entity.Employee;
import com.automobile.exception.NotfoundException;
import com.automobile.pojos.EmployeeDto;
import com.automobile.repository.EmployeeRepo;

@Service
public class EmployeeServiceImpl implements EmployeeService{

	@Autowired
	private AddressService addressService;
	
	@Autowired
	private DepartmentService departmentService;
	
	private final EmployeeRepo employeeRepo;
	
	public EmployeeServiceImpl(EmployeeRepo employeeRepo) {
		this.employeeRepo = employeeRepo;
	}

	@Override
	public Employee saveEmployee(@Valid EmployeeDto employeeDto) {
		Employee  employee=new Employee();
		employee.setEmpName(employeeDto.getEmpName());
		employee.setEmpSurname(employeeDto.getEmpSurname());
		employee.setAddress(addressService.fetchAddressById(employeeDto.getAddressId()));
		employee.setDepartment(departmentService.fetchDepartmentById(employeeDto.getDeptId()));
		return employeeRepo.save(employee);
	}

	@Override
	public List<Employee> fetchEmployeeList() {
		return employeeRepo.findAll(Sort.by(Sort.Direction.ASC, "empId"));
	}

	@Override
	public Employee fetchEMployeeById(@Valid int empId) {
		return employeeRepo.findById(empId).orElseThrow(() -> new NotfoundException("Employee Id not found !!!"));
	}

	@Override
	public void deleteEmployeeyId(@Valid int empId) {
		Employee employee=fetchEMployeeById(empId);
		employeeRepo.delete(employee);
	}

	@Override
	public Employee updateEmployee(@Valid EmployeeDto employeeDto, int empId) {
		Employee updateEmp=fetchEMployeeById(empId);
		updateEmp.setEmpName(employeeDto.getEmpName());
		updateEmp.setEmpSurname(employeeDto.getEmpSurname());
		updateEmp.setAddress(addressService.fetchAddressById(employeeDto.getAddressId()));
		updateEmp.setDepartment(departmentService.fetchDepartmentById(employeeDto.getDeptId()));
		return employeeRepo.save(updateEmp);
	}

}

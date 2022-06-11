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

import com.automobile.entity.Employee;
import com.automobile.pojos.EmployeeDto;
import com.automobile.service.EmployeeService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

	private final EmployeeService employeeService;

	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@PostMapping
	public ResponseEntity<Employee> saveEmployee(@Valid @RequestBody EmployeeDto employeeDto)  {
		return ResponseEntity.ok(employeeService.saveEmployee(employeeDto));
	}
	
	@GetMapping
	public ResponseEntity<List<Employee>> getEMployees(){
		return ResponseEntity.ok(employeeService.fetchEmployeeList());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable(name = "id") int empId){
		return ResponseEntity.ok(employeeService.fetchEMployeeById(empId));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Employee> deleteEmployeeById(@PathVariable(name = "id") int empId){
		employeeService.deleteEmployeeyId(empId);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Employee> updateEmployee(@Valid @RequestBody EmployeeDto employeeDto, @PathVariable(name = "id") int empId)
	{
		return ResponseEntity.ok(employeeService.updateEmployee(employeeDto, empId));
	}
}

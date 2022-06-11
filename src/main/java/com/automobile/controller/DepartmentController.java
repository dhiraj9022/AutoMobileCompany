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
import com.automobile.entity.Department;
import com.automobile.pojos.DepartmentDto;
import com.automobile.service.DepartmentService;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

	private final DepartmentService departmentService;

	public DepartmentController(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	@PostMapping
	public ResponseEntity<Department> saveDepartment(@Valid @RequestBody DepartmentDto departmentDto){
		return ResponseEntity.ok(departmentService.saveDepartment(departmentDto));
	}
	
	@GetMapping
	public ResponseEntity<List<Department>> getDepartment(){
		return ResponseEntity.ok(departmentService.fetchDepartmentList());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Department> getDepartmentById(@PathVariable(name = "id") int deptId){
		return ResponseEntity.ok(departmentService.fetchDepartmentById(deptId));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Department> deleteDepartmentById(@PathVariable(name = "id") int deptId){
		departmentService.deleteDepartmentById(deptId);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Department> updateDepartment(@Valid @RequestBody DepartmentDto departmentDto, @PathVariable(name = "id") int deptId) 
	{
		return ResponseEntity.ok(departmentService.updateDepartment(departmentDto, deptId));
	}
}

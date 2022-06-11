package com.automobile.pojos;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.automobile.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDto {
	
	private int deptId;
	
	@NotNull
	@NotBlank(message = "Department Name should not be blank")
	private String deptName;
	
	@NotNull(message = "Company Id should not be zero")
	private int companyId;
	
	private List<Employee> employees=new ArrayList<>();
}

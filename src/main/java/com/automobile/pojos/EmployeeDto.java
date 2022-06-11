package com.automobile.pojos;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import com.automobile.entity.Address;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
	
	private int empId;
	
	@NotNull
	@NotBlank(message = "Employee Name should not be blank")
	private String empName;
	
	@NotNull
	@NotBlank(message = "Employee Surname should not be blank")
	private String empSurname;
	
	@NotNull(message = "Address Id should not be Zero")
	private int addressId;
	
	@NotNull(message = "Department Id should not be Zero")
	private int deptId;
	
	private Address address;
}

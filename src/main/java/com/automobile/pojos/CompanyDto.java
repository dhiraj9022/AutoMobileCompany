package com.automobile.pojos;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.automobile.entity.Car;
import com.automobile.entity.Department;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDto {

	private int companyId;
	
	@NotNull
	@NotBlank(message = "Company Name should not be blank")
	private String companyName;
	
	private List<Car> cars=new ArrayList<>();

	private List<Department> departments=new ArrayList<>();
}

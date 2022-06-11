package com.automobile.pojos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarDto {

	private int carId;

	@Size(max = 10, message = "Size must be 10 ")
	@NotNull(message = "Registraion Number must be Number and Character")
	private String registrationNumber;
	
	@NotNull(message = "Company Id should not be zero")
	private int companyId;
}

package com.automobile.pojos;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {

	private int addressId;

	@NotNull(message = "House Number shuould not be zero")
	private int houseNumber;
	
	@NotBlank(message = "Street should not be blank")
	private String street;
	
	@NotNull(message = "Zipcode should not be blank")
	private int zipcode;
}

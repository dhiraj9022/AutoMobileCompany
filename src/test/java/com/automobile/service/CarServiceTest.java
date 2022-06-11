package com.automobile.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.automobile.entity.Car;
import com.automobile.entity.Company;
import com.automobile.entity.Car;
import com.automobile.repository.CarRepo;
import com.automobile.repository.CompanyRepo;
import com.automobile.repository.CarRepo;
import com.fasterxml.jackson.core.JsonProcessingException;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class CarServiceTest {
	
	@Autowired
	private CompanyService companyService;

	@MockBean
	private CompanyRepo companyRepo;

	@MockBean
	private CarRepo carRepo;
	
	private CarService carService;

	private Company company;
	
	private Car car;

	@BeforeEach
	public void setUp() {

		companyService = new CompanyServiceImpl(companyRepo);
		carService = new CarServiceImpl(carRepo);
	}

	public void carCompany() {
		company = new Company();
		company.setCompanyId(1);
		company.setCompanyName("Bently company");
	
		car=new Car();
		car.setRegistrationNumber("AB78CD7890");
		car.setCompany(company);
	}
	
	@Test
	@DisplayName("Save Car Test")
	@Order(1)
	public void whenSaveCar_shouldReturnCar() throws JsonProcessingException, Exception {

		carCompany();
		
		when(carRepo.save(car)).thenReturn(car);

		Car savedCar = carRepo.save(car);
		assertThat(savedCar.getCarId()).isNotNull();
		System.out.println(savedCar);
	}
	
	@Test
	@DisplayName("Fetch List Of Car Test")
	@Order(2)
	public void whenFetchListCar_shouldReturnListCar() throws JsonProcessingException, Exception {

		Company company1 = new Company();
		company1.setCompanyId(1);
		company1.setCompanyName("Juagar company");
		
		Company company2 = new Company();
		company1.setCompanyId(2);
		company2.setCompanyName("Maruti company");
		
		Car Car1=new Car();
		Car1.setCompany(company1);
		Car1.setRegistrationNumber("AP78MN7896");
		
		Car Car2=new Car();
		Car2.setCompany(company2);
		Car2.setRegistrationNumber("AP25NM7896");
		
		List<Car> Cars=new ArrayList<>();
		Cars.add(Car1);
		Cars.add(Car2);
		
		when(carRepo.findAll()).thenReturn(Cars);

		List<Car> fetchCars = carService.fetchCarList();
		assertThat(fetchCars.size()).isGreaterThan(0);
		System.out.println(fetchCars);
	}

	@Test
	@DisplayName("Fetch CarId Id Test")
	@Order(3)
	public void whenFetchCarById_shouldReturnCar() throws JsonProcessingException, Exception {

		carCompany();
		
		when(carRepo.findById(car.getCarId())).thenReturn(Optional.of(car));

		Optional<Car> fetchCar = carRepo.findById(car.getCarId());
		assertThat(fetchCar).isNotNull();
	}
	
	@Test
	@DisplayName("Update Car Test")
	@Order(4)
	public void whenUpdateCar_shouldReturnNewCar() throws JsonProcessingException, Exception {

		carCompany();
		
		when(carRepo.findById(car.getCarId())).thenReturn(Optional.of(car));
		when(carRepo.save(car)).thenReturn(car);

		Car updateCar = carRepo.save(car);
		assertThat(updateCar.getCarId()).isNotNull();
	}
	
	@Test
	@DisplayName("Delete CarBy Id Test")
	@Order(5)
	public void whenDeleteCarById_DoesntReturnCar()throws JsonProcessingException, Exception {

		carCompany();
		
		when(carRepo.findById(car.getCarId())).thenReturn(Optional.of(car));
		
		carRepo.delete(car);
		verify(carRepo, times(1)).delete(car);
	}
	
}
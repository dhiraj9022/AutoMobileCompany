package com.automobile.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.automobile.entity.Car;
import com.automobile.exception.NotfoundException;
import com.automobile.pojos.CarDto;
import com.automobile.repository.CarRepo;

@Service
public class CarServiceImpl implements CarService{

	@Autowired
	private CompanyService companyService;
	
	private final CarRepo carRepo;
	
	public CarServiceImpl(CarRepo carRepo) {
		this.carRepo = carRepo;
	}

	@Override
	public Car saveCar(@Valid CarDto carDto) {
		Car newCar=new Car();
		newCar.setRegistrationNumber(carDto.getRegistrationNumber());
		newCar.setCompany(companyService.fetchCompanyById(carDto.getCompanyId()));
		return carRepo.save(newCar);
	}

	@Override
	public List<Car> fetchCarList() {
		return carRepo.findAll(Sort.by(Sort.Direction.ASC, "carId"));
	}

	@Override
	public Car fetchCarById(@Valid int carId) {
		return carRepo.findById(carId).orElseThrow(() -> new NotfoundException("Car Id not found !!!"));
	}

	@Override
	public void deleteCarById(@Valid int carId) {
		Car car=fetchCarById(carId);
		carRepo.delete(car);
	}

	@Override
	public Car updateCar(@Valid CarDto carDto, int carId) {
		Car updateCar=fetchCarById(carId);
		updateCar.setRegistrationNumber(carDto.getRegistrationNumber());
		updateCar.setCompany(companyService.fetchCompanyById(carDto.getCompanyId()));
		return carRepo.save(updateCar);
	}

}

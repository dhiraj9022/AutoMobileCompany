package com.automobile.service;

import java.util.List;

import javax.validation.Valid;

import com.automobile.entity.Car;
import com.automobile.pojos.CarDto;

public interface CarService {
	public Car saveCar(@Valid CarDto carDto);

	public List<Car> fetchCarList();
	
	public Car fetchCarById(int carId);
	
	public void deleteCarById(int carId);
	
	public Car updateCar(@Valid CarDto carDto, int carId);
}

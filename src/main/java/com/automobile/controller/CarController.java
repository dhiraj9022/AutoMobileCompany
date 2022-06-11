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

import com.automobile.entity.Car;
import com.automobile.pojos.CarDto;
import com.automobile.service.CarService;

@RestController
@RequestMapping("/api/cars")
public class CarController {

	private final CarService carService;

	public CarController(CarService carService) {
		this.carService = carService;
	}

	@PostMapping
	public ResponseEntity<Car> saveCar(@Valid @RequestBody CarDto carDto) {
		return ResponseEntity.ok(carService.saveCar(carDto));
	}
	
	@GetMapping
	public ResponseEntity<List<Car>> getCars(){
		return ResponseEntity.ok(carService.fetchCarList());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Car> getCarById(@PathVariable(name = "id") int carId){
		return ResponseEntity.ok(carService.fetchCarById(carId));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Car> deleteCarById(@PathVariable(name = "id") int carId){
		carService.deleteCarById(carId);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Car> updateCar(@Valid @RequestBody CarDto carDto, @PathVariable(name = "id") int carId) 
	{
		return ResponseEntity.ok(carService.updateCar(carDto, carId));
	}
}

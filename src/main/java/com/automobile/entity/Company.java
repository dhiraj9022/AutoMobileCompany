package com.automobile.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "companies")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CompanyId")
	private int companyId;
	
	@Column(name = "CompanyName")
	private String companyName;

	@OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
	@JsonIgnoreProperties("companies")
	private List<Car> cars = new ArrayList<>();

	@OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
	@JsonIgnoreProperties("companies")
	private List<Department> departments = new ArrayList<>();
}

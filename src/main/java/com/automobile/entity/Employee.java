package com.automobile.entity;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "employees")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "EmpId")
	private int empId;
	
	@Column(name = "EmpName")
	private String empName;
	
	@Column(name = "EmpSurname")
	private String empSurname;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "dept_id", referencedColumnName = "deptId")
	private Department department;
	
	@OneToOne
	@JoinColumn(name = "address_id", referencedColumnName = "addressId")
	@JsonIgnoreProperties("employees")
	private Address address;
}

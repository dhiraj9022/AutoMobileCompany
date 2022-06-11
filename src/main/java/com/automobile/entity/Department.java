package com.automobile.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "departments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Department {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DeptId")
	private int deptId;
	
	@Column(name = "DeptName")
	private String deptName;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "company_id", referencedColumnName = "companyId")
	private Company company;
	
	@OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
	@JsonIgnoreProperties("departments")
	private List<Employee> employees=new ArrayList<>();
}

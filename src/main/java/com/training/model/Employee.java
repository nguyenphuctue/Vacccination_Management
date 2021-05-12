package com.training.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "employee")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

	@Id
	@Column(name = "employee_id")
	private String employeeId;

	@Column(name = "address")
	private String address;

	@Column(name = "date_of_brith")
	private Date dateOfBirth;

	@Column(name = "email", length = 100)
	private String email;

	@Column(name = "employee_name", length = 100)
	private String employeeName;

	@Column(name = "gender")
	private int gender;

	@Column(name = "image")
	private String image;

	@Column(name = "password")
	private String password;

	@Column(name = "phone", length = 20)
	private String phone;

	@Column(name = "position", length = 100)
	private String position;

	@Column(name = "user_name")
	private String userName;

	@Column(name = "working_place")
	private String workingPlace;
	
	@Column(columnDefinition = "VARCHAR(MAX)")
	private String base64Img;
	
	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinTable(
		name = "employee_role",
		joinColumns = @JoinColumn(name = "employee_id"),
		inverseJoinColumns = @JoinColumn(name = "role_id")
	)
	private List<Role> roles;
	
	@Column(name = "status_save")
	private int statusSave;
	
	

}

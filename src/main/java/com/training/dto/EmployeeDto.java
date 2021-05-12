package com.training.dto;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

import com.training.model.Role;
import com.training.validator.Age;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
	@NotEmpty(message = "Id must be not empty!")
	private String employeeId;
	
	@NotEmpty(message = "Adress must be not empty!")
	private String address;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "Date of birth must be not empty!")
	@PastOrPresent(message = "{MSG10}")
	@Age
	private Date dateOfBirth;
	
	@NotEmpty
	@Email(message = "{MSG5}")
	private String email;
	
	@NotEmpty(message = "Employee name must be not empty!")
	private String employeeName;
	
	@NotNull
	private int gender;
	
	private String image;
	
	@Pattern(regexp = "(0)+([0-9]{9})", message = "{MSG9}")
	private String phone;
	
	private String position;
	
	private String workingPlace;
	
	private String base64Img;
	
	List<Role> roles;
	
	private int statusSave;

}

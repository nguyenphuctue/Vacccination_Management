package com.training.dto;

import java.util.Date;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerReportDto {
	
	private long customerId;
	private String fullName;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;
	private String address;
	private String identityCard;
	private Set<InjectionResultRpDto> injectionResults;
}

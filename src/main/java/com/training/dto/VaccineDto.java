package com.training.dto;

import java.util.Date;

import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

import com.training.model.VaccineType;

import lombok.Data;

@Data
public class VaccineDto {
	
	@NotEmpty
	private String vaccineId;

	private String contraindication;

	private String indication;

	private int numberOfInjection;
	
	@NotEmpty
	private String origin;

	private boolean active;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date timeBeginNextInjection;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date timeEndNextInjection;

	private String usage;
	
	@NotEmpty
	private String vaccineName;

	private int statusSave;
	
	private VaccineType vaccineType;
}

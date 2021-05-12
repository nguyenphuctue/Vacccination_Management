package com.training.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class VaccineTypeDto {

	@NotNull
	private String vaccineTypeId;

	private String description;

	@NotNull
	private String vaccineTypeName;

	@NotNull
	private boolean vaccineTypeStatus;

	private String image;

	private String base64Img;
}

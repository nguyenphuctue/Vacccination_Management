package com.training.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vaccine_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VaccineType {

	@Id
	@Column(name = "vaccine_type_id", length = 36)
	private String vaccineTypeId;

	@Column(name = "description", length = 200)
	private String description;

	@Column(name = "vaccine_type_name", length = 50)
	private String vaccineTypeName;

	@Column(name = "vaccine_type_status")
	private boolean vaccineTypeStatus;

	@Column(name = "image")
	private String image;
	
	@Column(columnDefinition = "VARCHAR(MAX)")
	private String base64Img;
	
	@OneToMany(mappedBy = "vaccineType")
	private Set<Vaccine> vaccines;

}

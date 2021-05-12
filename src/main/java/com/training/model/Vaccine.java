package com.training.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "vaccine")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vaccine {

	@Id
	@Column(name = "vaccine_id", length = 36)
	private String vaccineId;

	@Column(name = "contraindication", length = 200)
	private String contraindication;

	@Column(name = "indication", length = 200)
	private String indication;

	@Column(name = "number_of_injection")
	private int numberOfInjection;

	@Column(name = "origin", length = 50)
	private String origin;

	@Column(name = "active")
	private boolean active;

	@Column(name = "time_begin_next_injection")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date timeBeginNextInjection;

	@Column(name = "time_end_next_injection")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date timeEndNextInjection;

	@Column(name = "usage", length = 200)
	private String usage;

	@Column(name = "vaccine_name")
	private String vaccineName;

	@Column(name = "status_save")
	private int statusSave;

	@OneToMany(mappedBy = "vaccine1")
	private Set<InjectionSchedule> injectionSchedules;

	@OneToMany(mappedBy = "vaccine")
	private Set<InjectionResult> injectionResults;

	@ManyToOne
	@JoinColumn(name = "vaccine_type_id")
	private VaccineType vaccineType;

}

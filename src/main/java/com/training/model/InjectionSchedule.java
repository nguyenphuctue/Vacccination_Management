package com.training.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "injection_schedule")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InjectionSchedule {

	@Id
	@Column(name = "injection_schedule_id", length = 36)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long injectionScheduleId;

	@Column(name = "description", length = 1000)
	private String description;

	@Column(name = "end_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date endDate;

	@Column(name = "place")
	private String place;

	@Column(name = "start_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date startDate;

	@ManyToOne
	@JoinColumn(name = "vaccine_id")
	private Vaccine vaccine1;

}

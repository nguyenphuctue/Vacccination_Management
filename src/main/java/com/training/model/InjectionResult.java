package com.training.model;

import java.util.Date;
import java.util.Objects;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "injection_result")
@Getter
@Setter
@AllArgsConstructor
public class InjectionResult {

	@Id
	@Column(name = "injection_result_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long injectionResultId;

	@Column(name = "injection_date")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date injectionDate;
	
	@Column(name = "injection_place")
	private String injectionPlace;

	@Column(name = "next_injection_date")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date nextInjectionDate;

	@Column(name = "number_of_injection")
	private int numberOfInjection;

	@Column(name = "prevention")
	private String prevention;
	
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;
	
	@ManyToOne
	@JoinColumn(name = "vaccine_id")
	private Vaccine vaccine;

	@Column(name = "code")
	private String code;

	@Column(name = "status_save")
	private int statusSave;

    public InjectionResult() {
    	this.customer = new Customer();
    	this.vaccine = new Vaccine();
    }

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		InjectionResult that = (InjectionResult) o;
		return injectionResultId == that.injectionResultId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(injectionResultId);
	}
}

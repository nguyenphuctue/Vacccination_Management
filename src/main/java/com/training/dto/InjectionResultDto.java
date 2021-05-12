package com.training.dto;

import com.training.model.Customer;
import com.training.model.Vaccine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InjectionResultDto {

	@NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long injectionResultId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date injectionDate;

    private String injectionPlace;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date nextInjectionDate;

    private int numberOfInjection;

    private String prevention;

    private Customer customer;

    private Vaccine vaccine;

    private String customerStr;

    private String vaccineStr;

    public void setCustomerStr() {
        this.customerStr = this.customer.getUserName() + " - " + this.customer.getFullName();
    }

	public void setVaccineStr() {
		this.vaccineStr = this.vaccine.getVaccineName();
	}
}


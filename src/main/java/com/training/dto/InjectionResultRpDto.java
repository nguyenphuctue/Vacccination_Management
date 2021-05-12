package com.training.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import com.training.model.Customer;
import com.training.model.Vaccine;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InjectionResultRpDto {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long injectionResultId;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date injectionDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date nextInjectionDate;

    private int numberOfInjection;
    
    private Customer customer;

    private Vaccine vaccine;
   
    private String prevention;
}


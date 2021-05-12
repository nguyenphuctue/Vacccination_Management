package com.training.repository;

import java.util.Date;
import java.util.List;

import com.training.model.Vaccine;

public interface VaccineCustomRepository {
	List<Vaccine> findByOptions( Date timeBeginNextInjection, Date timeEndNextInjection,String vaccineTypeName, String origin);
}

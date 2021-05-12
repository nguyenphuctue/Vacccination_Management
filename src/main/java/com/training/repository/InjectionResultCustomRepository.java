package com.training.repository;

import java.util.Date;
import java.util.List;

import com.training.model.InjectionResult;

public interface InjectionResultCustomRepository {
	List<InjectionResult> findByOptions(Date fromDate, Date toDate, String prevention, String vaccineTypeName);
}

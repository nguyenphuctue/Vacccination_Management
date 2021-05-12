package com.training.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.training.model.Customer;
import com.training.model.Vaccine;
import org.springframework.data.domain.Page;

import com.training.dto.InjectionResultRpDto;
import com.training.model.InjectionResult;

public interface InjectionResultService {

    List<InjectionResult> findAll();
    Optional<InjectionResult> findByInjectionResultId(long id);
    InjectionResult save(InjectionResult injectionResult);
    Page<InjectionResult> findPaginated(int pageNo, int pageSize, String keyword);
    void deleteById(long id);
    void deleteByCustomer(Customer customer);
    void deleteByVaccine(Vaccine vaccine);
    List<InjectionResultRpDto> findAllRp();
    List<InjectionResultRpDto> findByOptions(Date fromDate, Date toDate, String prevention, String vaccineTypeName);
    List<Integer> getListYear();
	List<Object[]> getlistByYear(int year);
}

package com.training.service;

import com.training.dto.CustomerDto;
import com.training.dto.CustomerInjectDto;
import com.training.dto.CustomerReportDto;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface CustomerService {
    List<CustomerDto> findAll();
    List<CustomerInjectDto> findAllCI();
    CustomerDto save(CustomerDto customer);
    Optional<CustomerDto> findById(long id);
    Optional<CustomerDto> findByUserName(String userName);
    Optional<CustomerDto> findByEmail(String email);
    Page<CustomerDto> findPaginated(int pageNo, int pageSize, String sortField, String sortDir, String keyword);
    void deleteById(long id);
    List<CustomerReportDto> findByOptions(Date fromDate, Date toDate, String fullName, String address);
    List<CustomerReportDto> findAllRp();
    List<Object[]> getlistByYear(int year);
    
}

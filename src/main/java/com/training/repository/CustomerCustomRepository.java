package com.training.repository;

import java.util.Date;
import java.util.List;


import com.training.model.Customer;

public interface CustomerCustomRepository {
	List<Customer> findByOptions(Date fromDate, Date toDate, String fullName, String address);

}

package com.training.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.training.model.Customer;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CustomerCustomRepositoryImpl implements CustomerCustomRepository {
	
	private final EntityManager entityManager;
	
	@Override
	public List<Customer> findByOptions(Date fromDate, Date toDate, String fullName, String address) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Customer> createQuery = criteriaBuilder.createQuery(Customer.class);
		Root<Customer> root = createQuery.from(Customer.class);
		createQuery.select(root);
		List<Predicate> predicates = new ArrayList<>();
		if (fromDate != null) {
			predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("dateOfBirth"), fromDate));
		}
		if (toDate != null) {
			predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("dateOfBirth"), toDate));
		}
		if (!fullName.isEmpty()) {
			predicates.add(criteriaBuilder.like(root.get("fullName"), "%" + fullName + "%"));
		} 
		if (!address.isEmpty()) {
			predicates.add(criteriaBuilder.like(root.get("address"), "%" + address + "%"));
		}
		createQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
		List<Customer> resultList = entityManager.createQuery(createQuery).getResultList();
		return resultList;
	}

}

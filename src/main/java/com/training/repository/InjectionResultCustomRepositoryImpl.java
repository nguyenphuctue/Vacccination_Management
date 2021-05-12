package com.training.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.training.model.InjectionResult;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class InjectionResultCustomRepositoryImpl implements InjectionResultCustomRepository {

	private final EntityManager entityManager;

	@Override
	public List<InjectionResult> findByOptions(Date fromDate, Date toDate, String prevention, String vaccineTypeName) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<InjectionResult> createQuery = criteriaBuilder.createQuery(InjectionResult.class);
		Root<InjectionResult> root = createQuery.from(InjectionResult.class);
		createQuery.select(root);
		if (fromDate != null && toDate != null) {
			createQuery.where(criteriaBuilder.between(root.get("injectionDate"), fromDate, toDate));
		} else {
			if (fromDate != null) {
				createQuery.where(criteriaBuilder.greaterThanOrEqualTo(root.get("injectionDate"), fromDate));
			}
			if (toDate != null) {
				createQuery.where(criteriaBuilder.lessThanOrEqualTo(root.get("injectionDate"), toDate));
			}
		}
		if (!prevention.isEmpty()) {
			createQuery.where(criteriaBuilder.like(root.get("prevention"), "%" + prevention + "%"));
		}
		if (!vaccineTypeName.isEmpty()) {
			createQuery.where(criteriaBuilder.like(root.get("vaccine").get("vaccineType").get("vaccineTypeName"),
					"%" + vaccineTypeName + "%"));
		}
		List<InjectionResult> resultList = entityManager.createQuery(createQuery).getResultList();
		return resultList;
	}

}

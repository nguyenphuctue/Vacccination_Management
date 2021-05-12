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

import com.training.model.Vaccine;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class VaccineCustomRepositoryImpl implements VaccineCustomRepository {

	private final EntityManager entityManager;

	@Override
	public List<Vaccine> findByOptions(Date timeBeginNextInjection, Date timeEndNextInjection, String vaccineTypeName,
			String origin) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Vaccine> createQuery = criteriaBuilder.createQuery(Vaccine.class);
		Root<Vaccine> root = createQuery.from(Vaccine.class);
		createQuery.select(root);
		List<Predicate> predicates = new ArrayList<>();
		if (timeBeginNextInjection != null) {
			predicates.add(criteriaBuilder.equal(root.get("timeBeginNextInjection"), timeBeginNextInjection));
		}
		if (timeEndNextInjection != null) {
			predicates.add(criteriaBuilder.equal(root.get("timeEndNextInjection"), timeEndNextInjection));
		}
		if (!vaccineTypeName.isEmpty()) {
			predicates.add(
					criteriaBuilder.like(root.get("vaccineType").get("vaccineTypeName"), "%" + vaccineTypeName + "%"));
		}
		if (!origin.isEmpty()) {
			predicates.add(criteriaBuilder.like(root.get("origin"), "%" + origin + "%"));
		}
		createQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
		List<Vaccine> resultList = entityManager.createQuery(createQuery).getResultList();
		return resultList;
	}

}

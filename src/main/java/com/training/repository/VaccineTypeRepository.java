package com.training.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.training.model.VaccineType;

@Repository
public interface VaccineTypeRepository extends JpaRepository<VaccineType, String> {
	Optional<VaccineType> findByVaccineTypeName(String vaccineTypeName);

	Optional<VaccineType> findById(String vaccineTypeId);
	
	List<VaccineType> findByVaccineTypeStatus(Boolean vaccineTypeStatus);

	@Query("SELECT vt FROM VaccineType vt WHERE"
			+ " vt.vaccineTypeId LIKE '%'||:keyword||'%'"
			+ " OR vt.vaccineTypeName LIKE '%'||:keyword||'%'")
    Page<VaccineType> findByKeyword(@Param("keyword") String keyword, Pageable pageable);
	
	@Modifying
	@Query("UPDATE VaccineType vc SET vc.vaccineTypeStatus = 0 WHERE vc.vaccineTypeId = :id")
	void changeStatus(@Param("id")  String id);
}

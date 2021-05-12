package com.training.repository;

import java.util.List;
import java.util.Optional;

import com.training.model.VaccineType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.training.model.Vaccine;

@Repository
public interface VaccineRepository extends JpaRepository<Vaccine, String>, VaccineCustomRepository {

	Optional<Vaccine> findById(String vaccineId);

	List<Vaccine> findByVaccineType(VaccineType vaccineType);

	@Query("SELECT v FROM Vaccine v "
			+ "LEFT JOIN v.vaccineType vt "
			+ "WHERE v.active = TRUE AND v.statusSave != 2 AND vt.vaccineTypeStatus = TRUE")
	List<Vaccine> findByActiveStatusSave();

	@Query("SELECT v FROM Vaccine v "
			+ "LEFT JOIN v.vaccineType vt "
			+ "WHERE v.statusSave != 2")
	List<Vaccine> findAllLogic();

	@Query("SELECT v FROM Vaccine v "
			+ "LEFT JOIN v.vaccineType vt "
			+ "WHERE v.statusSave != 2")
	Page<Vaccine> findAllLogic(Pageable pageable);

	@Query(value = "SELECT v FROM Vaccine v "
			+ "LEFT JOIN v.vaccineType vt "
			+ " WHERE (v.vaccineName LIKE '%'||:keyword||'%'"
			+ " OR v.vaccineId like '%'||:keyword||'%')"
			+ " AND v.statusSave != 2")
	Page<Vaccine> findByKeyword(@Param("keyword") String keyword, Pageable pageable);

	@Modifying
	@Query("UPDATE Vaccine v SET v.active = 0 WHERE v.vaccineId = :id")
	void changeStatus(@Param("id") String id);

	@Query("SELECT ISNULL(SUM(CASE WHEN MONTH(v.timeBeginNextInjection) = 1 THEN v.numberOfInjection END), 0)"
			+ " ,ISNULL(SUM(CASE WHEN MONTH(v.timeBeginNextInjection) = 2 THEN v.numberOfInjection END), 0)"
			+ " ,ISNULL(SUM(CASE WHEN MONTH(v.timeBeginNextInjection) = 3 THEN v.numberOfInjection END), 0)"
			+ " ,ISNULL(SUM(CASE WHEN MONTH(v.timeBeginNextInjection) = 4 THEN v.numberOfInjection END), 0)"
			+ " ,ISNULL(SUM(CASE WHEN MONTH(v.timeBeginNextInjection) = 5 THEN v.numberOfInjection END), 0)"
			+ " ,ISNULL(SUM(CASE WHEN MONTH(v.timeBeginNextInjection) = 6 THEN v.numberOfInjection END), 0)"
			+ " ,ISNULL(SUM(CASE WHEN MONTH(v.timeBeginNextInjection) = 7 THEN v.numberOfInjection END), 0)"
			+ " ,ISNULL(SUM(CASE WHEN MONTH(v.timeBeginNextInjection) = 8 THEN v.numberOfInjection END), 0)"
			+ " ,ISNULL(SUM(CASE WHEN MONTH(v.timeBeginNextInjection) = 9 THEN v.numberOfInjection END), 0)"
			+ " ,ISNULL(SUM(CASE WHEN MONTH(v.timeBeginNextInjection) = 10 THEN v.numberOfInjection END), 0)"
			+ " ,ISNULL(SUM(CASE WHEN MONTH(v.timeBeginNextInjection) = 11 THEN v.numberOfInjection END), 0)"
			+ " ,ISNULL(SUM(CASE WHEN MONTH(v.timeBeginNextInjection) = 12 THEN v.numberOfInjection END), 0)"
			+ " FROM Vaccine v"
			+ " LEFT JOIN v.vaccineType vt "
			+ " WHERE YEAR(v.timeBeginNextInjection) = :year AND v.statusSave != 2 AND vt.vaccineTypeStatus = TRUE")
	List<Object[]> getlistByYear(@Param("year") int year);

	@Query("SELECT DISTINCT YEAR(v.timeBeginNextInjection) FROM Vaccine v "
			+ "LEFT JOIN v.vaccineType vt "
			+ "WHERE v.statusSave != 2 AND vt.vaccineTypeStatus = TRUE "
			+ "ORDER BY YEAR(v.timeBeginNextInjection)")
	List<Integer> getListYear();

}

package com.training.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.training.model.InjectionSchedule;

@Repository
public interface InjectionScheduleRepository extends JpaRepository<InjectionSchedule, Long> {
	
//	@Query("SELECT i FROM InjectionSchedule i WHERE"
//			+ " i.vaccine1.active = TRUE AND i.vaccine1.statusSave != 2")
//	Page<InjectionSchedule> findAll(Pageable pageable);
	
//	@Query("SELECT i FROM InjectionSchedule i WHERE"
//			+ " (i.vaccine1.vaccineName LIKE '%'||:keyword||'%')"
//			+ " AND i.vaccine1.active = TRUE"
//			+ " AND i.vaccine1.statusSave != 2")
	@Query("SELECT i FROM InjectionSchedule i WHERE"
			+ " i.vaccine1.vaccineName LIKE '%'||:keyword||'%'")
	Page<InjectionSchedule> findByKeyword(@Param("keyword") String keyword, Pageable pageable);
	
}

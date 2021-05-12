package com.training.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.training.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String>{
	Optional<Employee> findByUserName(String userName);
	Optional<Employee> findById(String id);
	
	@Query("SELECT e FROM Employee e WHERE e.statusSave != 2")
	List<Employee> findAllLogic();
	
	@Query("SELECT e FROM Employee e WHERE e.statusSave != 2")
    Page<Employee> findAllLogic(Pageable pageable);
	
	@Query("SELECT e FROM Employee e WHERE"
			+ " (e.employeeId LIKE '%'||:keyword||'%'"
			+ " OR e.employeeName LIKE '%'||:keyword||'%')"
			+ " AND e.statusSave != 2")
    Page<Employee> findByKeyword(@Param("keyword") String keyword, Pageable pageable);
}

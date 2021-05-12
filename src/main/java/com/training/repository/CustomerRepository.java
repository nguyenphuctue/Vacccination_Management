package com.training.repository;

import com.training.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>, CustomerCustomRepository {

	@Query("SELECT c FROM Customer c WHERE c.statusSave = 0")
	List<Customer> findAll();

	@Query("SELECT c FROM Customer c WHERE c.statusSave = 0")
	Page<Customer> findAll(Pageable pageable);

    Optional<Customer> findByCustomerId(long id);

    Optional<Customer> findByUserName(String userName);

    Optional<Customer> findByEmail(String email);

    @Query(value = "select c from Customer c where c.fullName like '%'||:keyword||'%' or c.identityCard like '%'||:keyword||'%'")
    Page<Customer> findByKeyword(@Param("keyword") String keyword, Pageable pageable);
    
    @Query("SELECT ISNULL(SUM(CASE WHEN MONTH(ci.injectionDate) = 1 THEN ci.numberOfInjection END), 0)"
    		+ " ,ISNULL(SUM(CASE WHEN MONTH(ci.injectionDate) = 2 THEN ci.numberOfInjection END), 0)"
    		+ " ,ISNULL(SUM(CASE WHEN MONTH(ci.injectionDate) = 3 THEN ci.numberOfInjection END), 0)"
    		+ " ,ISNULL(SUM(CASE WHEN MONTH(ci.injectionDate) = 4 THEN ci.numberOfInjection END), 0)"
    		+ " ,ISNULL(SUM(CASE WHEN MONTH(ci.injectionDate) = 5 THEN ci.numberOfInjection END), 0)"
    		+ " ,ISNULL(SUM(CASE WHEN MONTH(ci.injectionDate) = 6 THEN ci.numberOfInjection END), 0)"
    		+ " ,ISNULL(SUM(CASE WHEN MONTH(ci.injectionDate) = 7 THEN ci.numberOfInjection END), 0)"
    		+ " ,ISNULL(SUM(CASE WHEN MONTH(ci.injectionDate) = 8 THEN ci.numberOfInjection END), 0)"
    		+ " ,ISNULL(SUM(CASE WHEN MONTH(ci.injectionDate) = 9 THEN ci.numberOfInjection END), 0)"
    		+ " ,ISNULL(SUM(CASE WHEN MONTH(ci.injectionDate) = 10 THEN ci.numberOfInjection END), 0)"
    		+ " ,ISNULL(SUM(CASE WHEN MONTH(ci.injectionDate) = 11 THEN ci.numberOfInjection END), 0)"
    		+ " ,ISNULL(SUM(CASE WHEN MONTH(ci.injectionDate) = 12 THEN ci.numberOfInjection END), 0)"
    		+ " FROM Customer c JOIN c.injectionResults ci WHERE YEAR(ci.injectionDate) = :year")
    List<Object[]> getlistByYear(@Param("year") int year);
}

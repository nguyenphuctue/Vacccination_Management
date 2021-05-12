package com.training.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.training.model.News;
@Repository
public interface NewsRepository extends JpaRepository<News, UUID>{
	Optional<News> findByContent(String content);
	Optional<News> findByNewsId(UUID newsId);

	@Query("SELECT n FROM News n WHERE"
			+ " n.active = 0"
			+ " AND n.content LIKE %?1%"
	)
	Page<News> searchNewsPages(String keyword, Pageable pageable);

	@Query("SELECT n FROM News n WHERE n.active = 0")
	Page<News> findAllLogic(Pageable pageable);



	@Query(value = "select n from News n where n.content like '%'||:keyword||'%' or n.title like '%'||:keyword||'%' or n.preview like '%'||:keyword||'%' " )
	Page<News> findByKeyword(@Param("keyword") String keyword, Pageable pageable);



}

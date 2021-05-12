package com.training.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.training.model.News;

public interface NewsService {
	List<News> findAll();

	News save(News news);

	Optional<News> findById(UUID id);

	void deleteById(UUID id);

	Page<News> searchNewsPages(String keyword, Pageable pageable);

	Page<News> findPaginated(int pageNo, int pageSize, String sortField, String sortDir, String keyword);

//	Page<News> findByPage(Pageable pageable);

	boolean IdAlready(String id);

}

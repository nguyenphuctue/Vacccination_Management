package com.training.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import com.training.model.News;
import com.training.repository.NewsRepository;
import com.training.service.NewsService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

	private final NewsRepository newsRepository;

	@Override
	public List<News> findAll() {
		return newsRepository.findAll();
	}

	@Override
	public News save(News news) {
		return newsRepository.save(news);
	}

	@Override
	public Optional<News> findById(UUID id) {
		return newsRepository.findById(id);
	}

	@Override
	public void deleteById(UUID newsId) {
		newsRepository.deleteById(newsId);
	}

	@Override
	public Page<News> searchNewsPages(String keyword, Pageable pageable) {
		return newsRepository.searchNewsPages(keyword, pageable);
	}

	@Override
	public Page<News> findPaginated(int page, int size, String sortField, String sortDir, String keyword) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
				: Sort.by(sortField).descending();
		Pageable pageable = PageRequest.of(page -1, size, sort);
		if (keyword.isEmpty()) {
			return (Page<News>) newsRepository.findAllLogic(pageable);
		}
		return (Page<News>) newsRepository.findByKeyword(keyword, pageable);
	}



	@Override
	public boolean IdAlready(String id) {
		return false;
	}



}

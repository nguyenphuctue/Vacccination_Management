package com.training.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.training.dto.VaccineInjectDto;
import org.springframework.data.domain.Page;

import com.training.model.Vaccine;
import org.springframework.web.multipart.MultipartFile;

import com.training.dto.VaccineDto;

public interface VaccineService {
	List<VaccineDto> findAll();

	List<VaccineInjectDto> findByActive(Boolean active);

	Vaccine save(Vaccine vaccine);

	VaccineDto save(VaccineDto vaccineDto);

	Optional<VaccineDto> findById(String vaccineId);

	void deleteById(String vaccineId);

	Page<VaccineDto> findPaginated(int pageNo, int pageSize, String sortField, String sortDir, String keyword);

	void changeStatus(String id);

	List<VaccineDto> findByOptions(Date timeBeginNextInjection, Date timeEndNextInjection, String vaccineTypeName,
			String origin);

	void excel(MultipartFile file);
	
	List<Object[]> getlistByYear(int year);
	
	List<Integer> getListYear();

}

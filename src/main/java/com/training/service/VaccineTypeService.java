package com.training.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.training.dto.VaccineTypeDto;
import com.training.model.VaccineType;

public interface VaccineTypeService {
	List<VaccineTypeDto> findAll();
	VaccineType save(VaccineTypeDto vaccineTypeDto);
	Optional<VaccineTypeDto> findById(String vaccineTypeId);
    Optional<VaccineType> findByVcId(String vaccineTypeId);
    List<VaccineTypeDto> findByVaccineTypeStatus(Boolean vaccineTypeStatus);
    void deleteById(String vaccineTypeId);
    Page<VaccineTypeDto> findPaginated(int page, int size, String sortField, String sortDir, String keyword);
    boolean IdAlready(String id);
    void changeStatus(String id);
}

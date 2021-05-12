package com.training.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.training.dto.VaccineScheDto;
import com.training.model.InjectionSchedule;

public interface InjectionScheduleService {
	List<InjectionSchedule> findAll();
    InjectionSchedule save(InjectionSchedule injectionSchedule);
    Optional<InjectionSchedule> findById(Long injectionScheduleId);
    
    List<VaccineScheDto> findAllVac();
    
    Page<InjectionSchedule> findPaginated(int page, int size, String sortField, String sortDir, String keyword);
}

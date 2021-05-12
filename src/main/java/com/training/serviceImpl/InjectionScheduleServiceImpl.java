package com.training.serviceImpl;

import com.training.dto.VaccineScheDto;
import com.training.model.InjectionSchedule;
import com.training.repository.InjectionScheduleRepository;
import com.training.repository.VaccineRepository;
import com.training.service.InjectionScheduleService;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;


@Service
@RequiredArgsConstructor
public class InjectionScheduleServiceImpl implements InjectionScheduleService {

    private final InjectionScheduleRepository injectionScheduleRepository;
    private final VaccineRepository vaccineRepository;
    private final ModelMapper modelMapper;
    
    @Override
    @Transactional
    public InjectionSchedule save(InjectionSchedule injectionSchedule) {
        return injectionScheduleRepository.save(injectionSchedule);
    }

    @Override
    @Transactional
    public Optional<InjectionSchedule> findById(Long injectionScheduleId) {
    	return injectionScheduleRepository.findById(injectionScheduleId);
    }

	@Override
	@Transactional
	public List<InjectionSchedule> findAll() {
		return injectionScheduleRepository.findAll();
	}

	@Override
	@Transactional
	public List<VaccineScheDto> findAllVac() {
		return vaccineRepository.findByActiveStatusSave().stream()
				.map(vaccine -> modelMapper.map(vaccine, VaccineScheDto.class))
				.collect(Collectors.toList());
	}

	@Override
	@Transactional
	public Page<InjectionSchedule> findPaginated(int page, int size, String sortField, String sortDir,
			String keyword) {	
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(page -1, size, sort);
        if (keyword.isEmpty()) {
        	return injectionScheduleRepository.findAll(pageable);
        }
        return injectionScheduleRepository.findByKeyword(keyword, pageable);
	}

}

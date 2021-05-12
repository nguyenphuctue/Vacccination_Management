package com.training.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.training.model.Vaccine;
import com.training.repository.VaccineRepository;
import com.training.service.InjectionResultService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.training.dto.VaccineTypeDto;
import com.training.model.VaccineType;
import com.training.repository.VaccineTypeRepository;
import com.training.service.VaccineTypeService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class VaccineTypeServiceImpl implements VaccineTypeService {

	private final VaccineTypeRepository vaccineTypeRepository;
	private final VaccineRepository vaccineRepository;
	private final InjectionResultService injectionResultService;
	private final ModelMapper modelMapper;

	@Override
	public List<VaccineTypeDto> findAll() {
		return vaccineTypeRepository.findAll().stream()
				.map(vaccineType -> modelMapper.map(vaccineType, VaccineTypeDto.class)).collect(Collectors.toList());
	}

	@Override
	public VaccineType save(VaccineTypeDto vaccineTypeDto) {
		VaccineType vaccineType = modelMapper.map(vaccineTypeDto, VaccineType.class);
		return vaccineTypeRepository.save(vaccineType);
	}

	@Override
	public Optional<VaccineTypeDto> findById(String vaccineTypeId) {
		return vaccineTypeRepository.findById(vaccineTypeId)
				.map(vaccineType -> modelMapper.map(vaccineType, VaccineTypeDto.class));
	}

	@Override
	public Optional<VaccineType> findByVcId(String vaccineTypeId) {
		return vaccineTypeRepository.findById(vaccineTypeId);
	}
	
	@Override
	public List<VaccineTypeDto> findByVaccineTypeStatus(Boolean status) {
		return vaccineTypeRepository.findByVaccineTypeStatus(status).stream()
				.map(vaccineType -> modelMapper.map(vaccineType, VaccineTypeDto.class)).collect(Collectors.toList());
	}

	@Override
	public void deleteById(String vaccineTypeId) {
		vaccineTypeRepository.deleteById(vaccineTypeId);
	}

	@Override
	public boolean IdAlready(String id) {
		return id != null && !findById(id).isPresent();
	}

	@Override
	public void changeStatus(String id) {
		List<Vaccine> vaccines = vaccineRepository.findByVaccineType(vaccineTypeRepository.findById(id).get());
		vaccines.forEach(injectionResultService::deleteByVaccine);
		vaccineTypeRepository.changeStatus(id);
	}

	@Override
	public Page<VaccineTypeDto> findPaginated(int page, int size, String sortField, String sortDir, String keyword) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
				: Sort.by(sortField).descending();
		Pageable pageable = PageRequest.of(page - 1, size, sort);
		if (keyword.isEmpty()) {
			return (Page<VaccineTypeDto>) vaccineTypeRepository.findAll(pageable)
					.map(vaccineType -> modelMapper.map(vaccineType, VaccineTypeDto.class));
		}
		return (Page<VaccineTypeDto>) vaccineTypeRepository.findByKeyword(keyword, pageable)
				.map(vaccineType -> modelMapper.map(vaccineType, VaccineTypeDto.class));
	}

}

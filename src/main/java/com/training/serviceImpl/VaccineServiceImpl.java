package com.training.serviceImpl;

import java.io.IOException;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;

import com.training.dto.VaccineInjectDto;
import com.training.service.InjectionResultService;
import com.training.utils.ExcelHelper;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.training.dto.VaccineDto;
import com.training.model.Vaccine;
import com.training.repository.VaccineRepository;
import com.training.service.VaccineService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class VaccineServiceImpl implements VaccineService {

	private final VaccineRepository vaccineRepository;
	private final InjectionResultService injectionResultService;
	private final ModelMapper mapper;

	@Override
	@Transactional
	public List<VaccineDto> findAll() {
		System.out.println(vaccineRepository.findAllLogic());
		return vaccineRepository.findAllLogic().stream().map(vaccine -> mapper.map(vaccine, VaccineDto.class))
				.collect(Collectors.toList());
	}

	@Override
	@Transactional
	public List<VaccineInjectDto> findByActive(Boolean active) {
		return vaccineRepository.findByActiveStatusSave().stream()
				.map(vaccine -> mapper.map(vaccine, VaccineInjectDto.class)).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public VaccineDto save(VaccineDto vaccineDto) {
		Vaccine vaccine = mapper.map(vaccineDto, Vaccine.class);
		return mapper.map(vaccineRepository.save(vaccine), VaccineDto.class);
	}

	@Override
	@Transactional
	public Optional<VaccineDto> findById(String vaccineId) {
		return vaccineRepository.findById(vaccineId).map(vaccine -> mapper.map(vaccine, VaccineDto.class));
	}

	@Override
	@Transactional
	public void deleteById(String vaccineId) {
		vaccineRepository.deleteById(vaccineId);
	}

	@Override
	@Transactional
	public Page<VaccineDto> findPaginated(int pageNo, int pageSize, String sortField, String sortDir, String keyword) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
				: Sort.by(sortField).descending();
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		if (keyword == null) {
			return vaccineRepository.findAllLogic(pageable).map(vaccine -> mapper.map(vaccine, VaccineDto.class));
		}
		return vaccineRepository.findByKeyword(keyword, pageable).map(vaccine -> mapper.map(vaccine, VaccineDto.class));
	}

	@Override
	@Transactional
	public void changeStatus(String id) {
		injectionResultService.deleteByVaccine(vaccineRepository.findById(id).get());
		vaccineRepository.changeStatus(id);
	}

	@Override
	@Transactional
	public void excel(MultipartFile file) {
		try {
			List<Vaccine> vaccines = ExcelHelper.excelVaccines(file.getInputStream());
			vaccineRepository.saveAll(vaccines);
		} catch (IOException e) {
			throw new RuntimeException("fail to store excel data: " + e.getMessage());
		}
	}

	@Override
	@Transactional
	public List<VaccineDto> findByOptions(Date timeBeginNextInjection, Date timeEndNextInjection,
			String vaccineTypeName, String origin) {
		return vaccineRepository.findByOptions(timeBeginNextInjection, timeEndNextInjection, vaccineTypeName, origin)
				.stream().map(vaccine -> mapper.map(vaccine, VaccineDto.class)).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public Vaccine save(Vaccine vaccine) {
		return vaccineRepository.save(vaccine);
	}

	@Override
	@Transactional
	public List<Object[]> getlistByYear(int year) {
		return vaccineRepository.getlistByYear(year);
	}

	@Override
	@Transactional
	public List<Integer> getListYear() {
		return vaccineRepository.getListYear();
	}

}

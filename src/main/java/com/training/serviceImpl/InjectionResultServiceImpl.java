package com.training.serviceImpl;

import java.util.*;
import java.util.stream.Collectors;

import com.training.model.Customer;
import com.training.model.Vaccine;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.training.dto.InjectionResultRpDto;
import com.training.model.InjectionResult;
import com.training.repository.InjectionResultRepository;
import com.training.service.InjectionResultService;

import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class InjectionResultServiceImpl implements InjectionResultService {

	private final ModelMapper mapper;
	private final InjectionResultRepository injectionResultRepository;

	@Override
	public List<InjectionResult> findAll() {
		return injectionResultRepository.findAll();
	}

	@Override
	public Optional<InjectionResult> findByInjectionResultId(long id) {
		return injectionResultRepository.findByInjectionResultId(id);
	}

	@Override
	public InjectionResult save(InjectionResult injectionResult) {
		String code = injectionResult.getCustomer().getUserName() + injectionResult.getPrevention() + injectionResult.getVaccine().getVaccineId();
		injectionResult.setCode(code);
		return injectionResultRepository.save(injectionResult);
	}

	@Override
	public Page<InjectionResult> findPaginated(int pageNo, int pageSize, String keyword) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		if (keyword == null || keyword.isEmpty()) {
			return injectionResultRepository.findAll(pageable);

		}
		List<InjectionResult> list = new ArrayList<>();
		list.addAll(injectionResultRepository.findByCustomerName(keyword, pageable).getContent());
		list.addAll(injectionResultRepository.findByVaccineName(keyword, pageable).getContent());
		Set<InjectionResult> set = new HashSet<>(list);
		List<InjectionResult> findByKeyword = new ArrayList<>(set);
		return new PageImpl<>(findByKeyword, pageable, findByKeyword.size());
	}

	@Override
	public void deleteById(long id) {
		InjectionResult injectionResult = injectionResultRepository.findByInjectionResultId(id).get();
		injectionResult.setStatusSave(1);
		injectionResultRepository.save(injectionResult);
	}

	@Override
	@Transactional
	public void deleteByCustomer(Customer customer) {
		List<InjectionResult> injectionResults = injectionResultRepository.findByCustomer(customer);
		injectionResults.forEach(injectionResult -> {
			injectionResult.setStatusSave(1);
			injectionResultRepository.save(injectionResult);
		});
	}

	@Override
	public void deleteByVaccine(Vaccine vaccine) {
		List<InjectionResult> injectionResults = injectionResultRepository.findByVaccine(vaccine);
		injectionResults.forEach(injectionResult -> {
			injectionResult.setStatusSave(1);
			injectionResultRepository.save(injectionResult);
		});
    }

	@Override
	public List<InjectionResultRpDto> findAllRp() {
		return injectionResultRepository.findAll().stream()
				.map(injectionResult -> mapper.map(injectionResult, InjectionResultRpDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public List<InjectionResultRpDto> findByOptions(Date fromDate, Date toDate, String prevention, String vaccineTypeName) {
		return injectionResultRepository.findByOptions(fromDate, toDate, prevention, vaccineTypeName)
				.stream()
				.map(injectionResult -> mapper.map(injectionResult, InjectionResultRpDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public List<Integer> getListYear() {
		return injectionResultRepository.getListYear();
	}

	@Override
	public List<Object[]> getlistByYear(int year) {
		return injectionResultRepository.getlistByYear(year);
	}
}

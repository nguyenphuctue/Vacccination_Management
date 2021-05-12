package com.training.serviceImpl;

import com.training.dto.CustomerDto;
import com.training.dto.CustomerInjectDto;
import com.training.dto.CustomerReportDto;
import com.training.model.Customer;
import com.training.repository.CustomerRepository;
import com.training.service.CustomerService;
import com.training.service.InjectionResultService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final ModelMapper mapper;
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final InjectionResultService injectionResultService;

    @Override
    public List<CustomerDto> findAll() {
        return customerRepository.findAll().stream().map(customer -> mapper.map(customer, CustomerDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<CustomerInjectDto> findAllCI() {
        return customerRepository.findAll().stream().map(customer -> mapper.map(customer, CustomerInjectDto.class)).collect(Collectors.toList());
    }

    @Override
    public CustomerDto save(CustomerDto customerDto) {
        Customer customer = mapper.map(customerDto, Customer.class);
        if (customer.getPassword() == null) {
            customer.setPassword(customerRepository.findByCustomerId(customer.getCustomerId()).get().getPassword());
        } else {
            customer.setPassword(passwordEncoder.encode(customerDto.getPassword()));
        }
        return mapper.map(customerRepository.save(customer), CustomerDto.class);
    }

    @Override
    public Optional<CustomerDto> findById(long id) {
        return customerRepository.findByCustomerId(id).map(customer -> mapper.map(customer, CustomerDto.class));
    }

    @Override
    public Optional<CustomerDto> findByUserName(String userName) {
        return customerRepository.findByUserName(userName).map(customer -> mapper.map(customer, CustomerDto.class));
    }

    @Override
    public Optional<CustomerDto> findByEmail(String email) {
        return customerRepository.findByEmail(email).map(customer -> mapper.map(customer, CustomerDto.class));
    }

    @Override
    public Page<CustomerDto> findPaginated(int pageNo, int pageSize, String sortField, String sortDir, String keyword) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo -1, pageSize, sort);
        if (keyword == null || keyword.isEmpty()) {
            return customerRepository.findAll(pageable).map(customer -> mapper.map(customer, CustomerDto.class));
        }
        return customerRepository.findByKeyword(keyword, pageable).map(customer -> mapper.map(customer, CustomerDto.class));
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        injectionResultService.deleteByCustomer(customerRepository.findByCustomerId(id).get());
        Customer customer = customerRepository.findByCustomerId(id).get();
        customer.setStatusSave((byte) 1);
        customerRepository.save(customer);
    }

	@Override
	public List<CustomerReportDto> findByOptions(Date fromDate, Date toDate, String fullName, String address) {
		return customerRepository.findByOptions(fromDate, toDate, fullName, address)
				.stream()
				.map(customer -> mapper.map(customer, CustomerReportDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public List<CustomerReportDto> findAllRp() {
		return customerRepository.findAll().stream()
				.map(customer -> mapper.map(customer, CustomerReportDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public List<Object[]> getlistByYear(int year) {
		return customerRepository.getlistByYear(year);
	}

}

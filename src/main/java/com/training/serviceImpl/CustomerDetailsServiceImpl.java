package com.training.serviceImpl;

import com.training.model.Customer;
import com.training.model.CustomerDetail;
import com.training.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerDetailsServiceImpl implements UserDetailsService {

    private final CustomerRepository customerRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Customer> optionalCustomer = customerRepository.findByUserName(username);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            CustomerDetail customerDetail = new CustomerDetail(customer);
            return (UserDetails) customerDetail;
        }
        throw new UsernameNotFoundException("Cannot found Customer");
    }
}

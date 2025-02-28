package com.exampleSecurityApp.service;

import com.exampleSecurityApp.entity.Customer;
import com.exampleSecurityApp.repo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomerService implements UserDetailsService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private CustomerRepository customerRepository;

    public boolean saveCustomer(Customer customer){

        String encodedPwd = bCryptPasswordEncoder.encode(customer.getPwd());
        customer.setPwd(encodedPwd);

        Customer savedCustomer = customerRepository.save(customer);
        return savedCustomer.getCid()!=null;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Customer customer = customerRepository.findByEmail(email);
        return new User(customer.getEmail(), customer.getPwd(), Collections.emptyList());

    }
}
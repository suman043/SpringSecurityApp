package com.exampleSecurityApp.service;

import com.exampleSecurityApp.entity.Customer;
import com.exampleSecurityApp.repo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

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
}
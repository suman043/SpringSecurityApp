package com.exampleSecurityApp.controller;

import com.exampleSecurityApp.entity.Customer;
import com.exampleSecurityApp.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerRestController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<String> registerCustomer(@RequestBody Customer customer){

        boolean status = customerService.saveCustomer(customer);

        if(status){
            return new ResponseEntity<>("Success", HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<>("Failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Customer customer){

        UsernamePasswordAuthenticationToken token = new
                UsernamePasswordAuthenticationToken(customer.getEmail(), customer.getPwd());

        //verify login details valid or not
        Authentication authenticate = authenticationManager.authenticate(token);

        boolean authenticatedStatus = authenticate.isAuthenticated();
        if(authenticatedStatus){
            return new ResponseEntity<>("Welcome",HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Failed", HttpStatus.BAD_REQUEST);
        }
    }
}
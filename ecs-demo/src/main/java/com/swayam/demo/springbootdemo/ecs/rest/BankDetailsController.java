package com.swayam.demo.springbootdemo.ecs.rest;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swayam.demo.springbootdemo.ecs.model.BankDetails;
import com.swayam.demo.springbootdemo.ecs.service.BankDetailsService;

@RestController
@RequestMapping("/api/v1/bank-details")
public class BankDetailsController {

    private final BankDetailsService bankDetailsService;

    public BankDetailsController(BankDetailsService bankDetailsService) {
	this.bankDetailsService = bankDetailsService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BankDetails> getBankDetails() {
	return bankDetailsService.getBankDetails();
    }

}

package com.swayam.demo.springbootdemo.dynamicclassesbytebuddy.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swayam.demo.springbootdemo.dynamicclassesbytebuddy.model.BankDetail;
import com.swayam.demo.springbootdemo.dynamicclassesbytebuddy.service.BankDetailService;

@RestController
@RequestMapping("/v1/bank-item")
public class BankDetailController {

    private final BankDetailService bankDetailService;

    public BankDetailController(BankDetailService bankDetailService) {
	this.bankDetailService = bankDetailService;
    }

    @GetMapping
    public Iterable<BankDetail> getBankDetails() {
	return bankDetailService.getBankDetails();
    }

}

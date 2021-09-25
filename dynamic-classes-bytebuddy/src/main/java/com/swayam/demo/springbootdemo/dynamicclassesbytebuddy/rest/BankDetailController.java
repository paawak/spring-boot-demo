package com.swayam.demo.springbootdemo.dynamicclassesbytebuddy.rest;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.swayam.demo.springbootdemo.dynamicclassesbytebuddy.model.BankDetail;
import com.swayam.demo.springbootdemo.dynamicclassesbytebuddy.service.BankDetailService;

@RestController
@RequestMapping("/bank-item")
public class BankDetailController {

    private final BankDetailService bankDetailService;

    public BankDetailController(BankDetailService bankDetailService) {
	this.bankDetailService = bankDetailService;
    }

    @RequestMapping(value = "/blocking", method = RequestMethod.GET)
    public List<BankDetail> getBankDetails() {
	return bankDetailService.getBankDetails();
    }

}

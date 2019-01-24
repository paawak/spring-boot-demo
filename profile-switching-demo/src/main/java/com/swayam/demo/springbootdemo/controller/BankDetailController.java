package com.swayam.demo.springbootdemo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.swayam.demo.springbootdemo.model.BankDetail;
import com.swayam.demo.springbootdemo.service.BankDetailService;

@RestController
@RequestMapping("/")
public class BankDetailController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BankDetailController.class);

    private final BankDetailService bankDetailService;

    public BankDetailController(BankDetailService bankDetailService) {
        this.bankDetailService = bankDetailService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<BankDetail> getBankDetails() {
        LOGGER.info("getting bank details");
        return bankDetailService.getBankDetails();
    }

}

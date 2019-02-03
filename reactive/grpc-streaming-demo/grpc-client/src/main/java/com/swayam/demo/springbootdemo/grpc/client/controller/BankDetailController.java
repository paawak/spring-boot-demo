package com.swayam.demo.springbootdemo.grpc.client.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.swayam.demo.springbootdemo.grpc.client.model.BankDetailForJson;
import com.swayam.demo.springbootdemo.grpc.client.service.BankDetailService;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/bank-item")
public class BankDetailController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BankDetailController.class);

    private final BankDetailService bankDetailService;

    public BankDetailController(BankDetailService bankDetailService) {
        this.bankDetailService = bankDetailService;
    }

    @RequestMapping(value = "/reactive", method = RequestMethod.GET)
    public Flux<BankDetailForJson> getBankDetailsReactive() {
        LOGGER.info("serving reactive content");
        return bankDetailService.getBankDetailsReactive();
    }

}

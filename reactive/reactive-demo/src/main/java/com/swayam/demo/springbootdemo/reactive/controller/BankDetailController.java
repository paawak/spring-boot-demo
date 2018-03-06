package com.swayam.demo.springbootdemo.reactive.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.swayam.demo.springbootdemo.reactive.model.BankDetail;
import com.swayam.demo.springbootdemo.reactive.service.BankDetailService;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/bank-item")
public class BankDetailController {

	private static final Logger LOGGER = LoggerFactory.getLogger(BankDetailController.class);

	private final BankDetailService bankDetailService;

	public BankDetailController(BankDetailService bankDetailService) {
		this.bankDetailService = bankDetailService;
	}

	@RequestMapping(value = "/blocking", method = RequestMethod.GET)
	public List<BankDetail> getBankDetails() {
		return bankDetailService.getBankDetails();
	}

	@RequestMapping(value = "/reactive", method = RequestMethod.GET)
	public Flux<BankDetail> getBankDetailsReactive() {
		LOGGER.info("serving reactive content");
		return bankDetailService.getBankDetailsReactive();
	}

}

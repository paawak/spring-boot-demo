package com.swayam.demo.springbootdemo.reactive.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.swayam.demo.springbootdemo.reactive.model.BankDetail;
import com.swayam.demo.springbootdemo.reactive.service.BankDetailService;

import reactor.core.publisher.Flux;

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

	@RequestMapping(value = "/reactive", method = RequestMethod.GET, produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Flux<BankDetail> getBankDetailsReactive() {
		return bankDetailService.getBankDetailsReactive();
	}

}

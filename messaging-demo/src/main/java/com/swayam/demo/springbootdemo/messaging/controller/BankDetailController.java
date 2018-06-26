package com.swayam.demo.springbootdemo.messaging.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.swayam.demo.springbootdemo.messaging.model.BankDetailSortOrder;
import com.swayam.demo.springbootdemo.messaging.service.pub.DataPublisherService;

@RestController
@RequestMapping("/bank-data")
public class BankDetailController {

	private final DataPublisherService dataPublisher;

	public BankDetailController(DataPublisherService dataPublisher) {
		this.dataPublisher = dataPublisher;
	}

	@RequestMapping(value = "/publish/{bankDetailGroup}", method = RequestMethod.GET)
	public String publishBankData(@PathVariable("bankDetailGroup") BankDetailSortOrder bankDetailGroup) {
		dataPublisher.publishData(bankDetailGroup);
		return "success";
	}

}

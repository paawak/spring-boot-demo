package com.swayam.demo.springbootdemo.reactive.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.swayam.demo.springbootdemo.reactive.dao.BankDetailDao;
import com.swayam.demo.springbootdemo.reactive.model.BankDetail;

@RestController
@RequestMapping("/bank-item")
public class BankDetailController {

	private final BankDetailDao bankDetailDao;

	public BankDetailController(BankDetailDao bankDetailDao) {
		this.bankDetailDao = bankDetailDao;
	}

	@RequestMapping(method = RequestMethod.GET)
	public List<BankDetail> getBankDetails() {
		return bankDetailDao.getBankDetails();
	}

}

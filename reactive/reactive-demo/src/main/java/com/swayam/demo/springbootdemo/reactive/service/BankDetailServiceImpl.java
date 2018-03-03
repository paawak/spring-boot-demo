package com.swayam.demo.springbootdemo.reactive.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.swayam.demo.springbootdemo.reactive.dao.BankDetailDao;
import com.swayam.demo.springbootdemo.reactive.model.BankDetail;

import reactor.core.publisher.Flux;

@Service
public class BankDetailServiceImpl implements BankDetailService {

	private final BankDetailDao bankDetailDao;

	public BankDetailServiceImpl(BankDetailDao bankDetailDao) {
		this.bankDetailDao = bankDetailDao;
	}

	@Override
	public List<BankDetail> getBankDetails() {
		return bankDetailDao.getBankDetails();
	}

	@Override
	public Flux<BankDetail> getBankDetailsReactive() {
		return Flux.fromIterable(bankDetailDao.getBankDetails());
	}

}

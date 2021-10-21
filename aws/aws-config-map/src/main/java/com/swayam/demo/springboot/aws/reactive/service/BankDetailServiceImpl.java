package com.swayam.demo.springboot.aws.reactive.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.swayam.demo.springboot.aws.reactive.dao.BankDetailDao;
import com.swayam.demo.springboot.aws.reactive.model.BankDetail;

import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

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
		return Flux.create((FluxSink<BankDetail> fluxSink) -> {
			bankDetailDao.publishBankDetails(fluxSink);
		});
	}

}

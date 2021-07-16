package com.swayam.demo.springbootdemo.reactive.service;

import java.util.List;

import com.swayam.demo.springbootdemo.reactive.model.BankDetail;

import reactor.core.publisher.Flux;

public interface BankDetailService {

	List<BankDetail> getBankDetails();

	Flux<BankDetail> getBankDetailsReactive();

}

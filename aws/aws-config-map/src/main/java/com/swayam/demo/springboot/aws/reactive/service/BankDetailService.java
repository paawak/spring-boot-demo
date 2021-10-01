package com.swayam.demo.springboot.aws.reactive.service;

import java.util.List;

import com.swayam.demo.springboot.aws.reactive.model.BankDetail;

import reactor.core.publisher.Flux;

public interface BankDetailService {

	List<BankDetail> getBankDetails();

	Flux<BankDetail> getBankDetailsReactive();

}

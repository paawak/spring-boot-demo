package com.swayam.demo.springbootdemo.grpc.service;

import java.util.List;

import com.swayam.demo.springbootdemo.grpc.model.BankDetail;

import reactor.core.publisher.Flux;

public interface BankDetailService {

	List<BankDetail> getBankDetails();

	Flux<BankDetail> getBankDetailsReactive();

}

package com.swayam.demo.springbootdemo.reactive.dao;

import java.util.List;

import com.swayam.demo.springbootdemo.reactive.model.BankDetail;

import reactor.core.publisher.FluxSink;

public interface BankDetailDao {

	List<BankDetail> getBankDetails();

	void publishBankDetails(FluxSink<BankDetail> fluxSink);

}
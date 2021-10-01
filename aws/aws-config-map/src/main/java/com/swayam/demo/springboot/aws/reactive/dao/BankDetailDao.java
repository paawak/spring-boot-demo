package com.swayam.demo.springboot.aws.reactive.dao;

import java.util.List;

import com.swayam.demo.springboot.aws.reactive.model.BankDetail;

import reactor.core.publisher.FluxSink;

public interface BankDetailDao {

	List<BankDetail> getBankDetails();

	void publishBankDetails(FluxSink<BankDetail> fluxSink);

}
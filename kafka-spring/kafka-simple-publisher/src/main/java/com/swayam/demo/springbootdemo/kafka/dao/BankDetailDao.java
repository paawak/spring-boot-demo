package com.swayam.demo.springbootdemo.kafka.dao;

import java.util.List;

import com.swayam.demo.springbootdemo.kafka.model.BankDetail;

import reactor.core.publisher.FluxSink;

public interface BankDetailDao {

	List<BankDetail> getBankDetails();

	void publishBankDetails(FluxSink<BankDetail> fluxSink);

}
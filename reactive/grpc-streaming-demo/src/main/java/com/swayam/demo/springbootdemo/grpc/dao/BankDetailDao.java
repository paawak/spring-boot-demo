package com.swayam.demo.springbootdemo.grpc.dao;

import java.util.List;

import com.swayam.demo.springbootdemo.grpc.model.BankDetail;

import reactor.core.publisher.FluxSink;

public interface BankDetailDao {

	List<BankDetail> getBankDetails();

	void publishBankDetails(FluxSink<BankDetail> fluxSink);

}
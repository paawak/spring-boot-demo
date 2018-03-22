package com.swayam.demo.springbootdemo.grpc.controller;

import org.springframework.stereotype.Service;

import com.swayam.demo.springbootdemo.grpc.proto.BankDetailDto;
import com.swayam.demo.springbootdemo.grpc.proto.BankDetailRequest;
import com.swayam.demo.springbootdemo.grpc.proto.BankDetailStreamerGrpc.BankDetailStreamerImplBase;
import com.swayam.demo.springbootdemo.grpc.service.BankDetailService;

import io.grpc.stub.StreamObserver;

@Service
public class BankDetailStreamer extends BankDetailStreamerImplBase {

    private final BankDetailService bankDetailService;

    public BankDetailStreamer(BankDetailService bankDetailService) {
	this.bankDetailService = bankDetailService;
    }

    @Override
    public void streamBankDetails(BankDetailRequest request, StreamObserver<BankDetailDto> responseObserver) {
	bankDetailService.getBankDetailsReactive(responseObserver);
    }

}

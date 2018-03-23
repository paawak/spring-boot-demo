package com.swayam.demo.springbootdemo.grpc.server.controller;

import org.springframework.stereotype.Service;

import com.swayam.demo.springbootdemo.grpc.server.service.BankDetailService;
import com.swayam.demo.springbootdemo.grpc.shared.proto.BankDetailDto;
import com.swayam.demo.springbootdemo.grpc.shared.proto.BankDetailRequest;
import com.swayam.demo.springbootdemo.grpc.shared.proto.BankDetailStreamerGrpc.BankDetailStreamerImplBase;

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

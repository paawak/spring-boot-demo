package com.swayam.demo.springbootdemo.grpc.dao;

import com.swayam.demo.springbootdemo.grpc.proto.BankDetailDto;

import io.grpc.stub.StreamObserver;

public interface BankDetailDao {

    void getBankDetailsReactive(StreamObserver<BankDetailDto> responseObserver);

}
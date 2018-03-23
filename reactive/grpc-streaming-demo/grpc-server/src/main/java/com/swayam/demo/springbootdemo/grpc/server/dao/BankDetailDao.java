package com.swayam.demo.springbootdemo.grpc.server.dao;

import com.swayam.demo.springbootdemo.grpc.shared.proto.BankDetailDto;

import io.grpc.stub.StreamObserver;

public interface BankDetailDao {

    void getBankDetailsReactive(StreamObserver<BankDetailDto> responseObserver);

}
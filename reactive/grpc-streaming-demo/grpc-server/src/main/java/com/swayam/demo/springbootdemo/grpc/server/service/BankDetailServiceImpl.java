package com.swayam.demo.springbootdemo.grpc.server.service;

import org.springframework.stereotype.Service;

import com.swayam.demo.springbootdemo.grpc.server.dao.BankDetailDao;
import com.swayam.demo.springbootdemo.grpc.shared.proto.BankDetailDto;

import io.grpc.stub.StreamObserver;

@Service
public class BankDetailServiceImpl implements BankDetailService {

    private final BankDetailDao bankDetailDao;

    public BankDetailServiceImpl(BankDetailDao bankDetailDao) {
	this.bankDetailDao = bankDetailDao;
    }

    @Override
    public void getBankDetailsReactive(StreamObserver<BankDetailDto> responseObserver) {
	bankDetailDao.getBankDetailsReactive(responseObserver);
    }

}

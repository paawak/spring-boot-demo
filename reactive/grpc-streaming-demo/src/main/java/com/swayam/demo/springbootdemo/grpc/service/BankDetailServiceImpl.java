package com.swayam.demo.springbootdemo.grpc.service;

import org.springframework.stereotype.Service;

import com.swayam.demo.springbootdemo.grpc.dao.BankDetailDao;
import com.swayam.demo.springbootdemo.grpc.proto.BankDetailDto;

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

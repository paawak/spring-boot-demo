package com.swayam.demo.springbootdemo.dynamicclassesbytebuddy.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.swayam.demo.springbootdemo.dynamicclassesbytebuddy.dao.BankDetailDao;
import com.swayam.demo.springbootdemo.dynamicclassesbytebuddy.model.BankDetail;

@Service
public class BankDetailServiceImpl implements BankDetailService {

    private final BankDetailDao bankDetailDao;

    public BankDetailServiceImpl(BankDetailDao bankDetailDao) {
	this.bankDetailDao = bankDetailDao;
    }

    @Override
    public List<BankDetail> getBankDetails() {
	return bankDetailDao.getBankDetails();
    }

}

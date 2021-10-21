package com.swayam.demo.springbootdemo.dynamicclassesbytebuddy.dao;

import org.springframework.data.repository.CrudRepository;

import com.swayam.demo.springbootdemo.dynamicclassesbytebuddy.model.BankDetail;

public interface BankDetailDao extends CrudRepository<BankDetail, Integer> {

}
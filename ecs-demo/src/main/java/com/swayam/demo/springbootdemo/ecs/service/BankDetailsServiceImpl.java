package com.swayam.demo.springbootdemo.ecs.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.swayam.demo.springbootdemo.ecs.model.BankDetails;
import com.swayam.demo.springbootdemo.ecs.repo.BankDetailsRepo;

@Service
public class BankDetailsServiceImpl implements BankDetailsService {

    private final BankDetailsRepo bankDetailsRepo;

    public BankDetailsServiceImpl(BankDetailsRepo bankDetailsRepo) {
	this.bankDetailsRepo = bankDetailsRepo;
    }

    @Override
    public List<BankDetails> getBankDetails() {
	return bankDetailsRepo.findAll();
    }

}

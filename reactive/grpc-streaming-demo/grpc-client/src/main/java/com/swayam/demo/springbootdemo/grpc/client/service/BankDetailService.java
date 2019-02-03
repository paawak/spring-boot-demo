package com.swayam.demo.springbootdemo.grpc.client.service;

import com.swayam.demo.springbootdemo.grpc.client.dto.HttpFriendlyBankDetail;

import reactor.core.publisher.Flux;

public interface BankDetailService {

    Flux<HttpFriendlyBankDetail> getBankDetailsReactive();

}

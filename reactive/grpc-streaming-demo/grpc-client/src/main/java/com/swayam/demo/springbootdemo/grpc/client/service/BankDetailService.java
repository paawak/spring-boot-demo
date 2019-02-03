package com.swayam.demo.springbootdemo.grpc.client.service;

import com.swayam.demo.springbootdemo.grpc.client.model.BankDetailForJson;

import reactor.core.publisher.Flux;

public interface BankDetailService {

    Flux<BankDetailForJson> getBankDetailsReactive();

}

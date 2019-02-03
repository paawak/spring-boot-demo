package com.swayam.demo.springbootdemo.grpc.client.service;

import com.swayam.demo.springbootdemo.grpc.shared.proto.BankDetailDto;

import reactor.core.publisher.Flux;

public interface BankDetailService {

    Flux<BankDetailDto> getBankDetailsReactive();

}

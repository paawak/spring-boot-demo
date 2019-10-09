package com.swayam.demo.springbootdemo.kafkastream.config;

import org.springframework.kafka.support.serializer.JsonSerde;

import com.swayam.demo.springbootdemo.kafkadto.BankDetail;

public class BankDetailSerde extends JsonSerde<BankDetail> {

}

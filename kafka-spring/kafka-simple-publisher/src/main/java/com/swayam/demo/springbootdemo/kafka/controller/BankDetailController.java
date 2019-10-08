package com.swayam.demo.springbootdemo.kafka.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.swayam.demo.springbootdemo.kafka.service.BankDetailService;
import com.swayam.demo.springbootdemo.kafkadto.BankDetail;

@RestController
@RequestMapping("/kafka")
public class BankDetailController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BankDetailController.class);

    private static final String TOPIC_NAME = "bank-details";

    private final KafkaTemplate<String, BankDetail> template;
    private final BankDetailService bankDetailService;

    public BankDetailController(KafkaTemplate<String, BankDetail> template, BankDetailService bankDetailService) {
        this.template = template;
        this.bankDetailService = bankDetailService;
    }

    @RequestMapping(value = "/send", method = RequestMethod.GET)
    public void getBankDetailsReactive() {
        LOGGER.info("sending messages to topic: {}", TOPIC_NAME);
        bankDetailService.getBankDetailsReactive().doOnNext(bankDetail -> template.send(TOPIC_NAME, Integer.toString(bankDetail.getId()), bankDetail))
                .subscribe();
    }

}

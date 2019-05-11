package com.swayam.demo.springbootdemo.cassandra.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.swayam.demo.springbootdemo.cassandra.model.BankDetail;
import com.swayam.demo.springbootdemo.cassandra.repo.BankDetailRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.GroupedFlux;

@RestController
@RequestMapping("/bank")
public class BankDetailRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BankDetailRestController.class);

    private final BankDetailRepository bankDetailRepository;

    public BankDetailRestController(BankDetailRepository bankDetailRepository) {
        this.bankDetailRepository = bankDetailRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Flux<BankDetail> getBankDetails() {
        LOGGER.info("serving reactive content");
        return bankDetailRepository.findAll();
    }

    @RequestMapping(value = "/group", method = RequestMethod.GET)
    public Flux<String> getBankDetailsGrouped() {
        LOGGER.info("grouping by");
        return bankDetailRepository.findAll().groupBy(BankDetail::getJob).map(GroupedFlux::key);
    }

}

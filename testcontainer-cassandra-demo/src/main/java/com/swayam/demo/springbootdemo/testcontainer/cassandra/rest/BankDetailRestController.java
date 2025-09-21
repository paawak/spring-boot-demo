package com.swayam.demo.springbootdemo.testcontainer.cassandra.rest;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.swayam.demo.springbootdemo.testcontainer.cassandra.model.BankDetail;
import com.swayam.demo.springbootdemo.testcontainer.cassandra.repo.BankDetailRepository;

@RestController
@RequestMapping("/bank")
public class BankDetailRestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(BankDetailRestController.class);

	private final BankDetailRepository bankDetailRepository;

	public BankDetailRestController(BankDetailRepository bankDetailRepository) {
		this.bankDetailRepository = bankDetailRepository;
	}

	@RequestMapping(method = RequestMethod.GET)
	public List<BankDetail> getBankDetails() {
		LOGGER.info("serving reactive content");
		return bankDetailRepository.findAll();
	}

	@RequestMapping(value = "/group", method = RequestMethod.GET)
	public Set<String> getBankDetailsGrouped() {
		LOGGER.info("grouping by");
		return bankDetailRepository.findAll().stream().collect(Collectors.groupingBy(BankDetail::getJob)).keySet();
	}

}

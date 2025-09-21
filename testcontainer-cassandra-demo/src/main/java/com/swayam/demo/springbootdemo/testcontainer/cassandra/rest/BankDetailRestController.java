package com.swayam.demo.springbootdemo.testcontainer.cassandra.rest;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

	@GetMapping
	public List<BankDetail> getBankDetails() {
		return bankDetailRepository.findAll();
	}

	@GetMapping("/group")
	public Set<String> getBankDetailsGrouped() {
		LOGGER.info("grouping by");
		return bankDetailRepository.findAll().stream().collect(Collectors.groupingBy(BankDetail::getJob)).keySet();
	}

}

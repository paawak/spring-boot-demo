package com.swayam.demo.opentracing.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.swayam.demo.opentracing.entity.Currency;
import com.swayam.demo.opentracing.service.CurrencyService;

@RestController
@RequestMapping("/currency")
public class CurrencyController {

	private final CurrencyService currencyService;

	public CurrencyController(CurrencyService currencyService) {
		this.currencyService = currencyService;
	}

	@RequestMapping(method = RequestMethod.POST)
	public Integer addCurrency(@RequestBody Currency currency) {
		return currencyService.addCurrency(currency);
	}

	@RequestMapping(method = RequestMethod.GET)
	public List<Currency> getAllCurrencies() {
		return currencyService.getAllCurrencies();
	}

}

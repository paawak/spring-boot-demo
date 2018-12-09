package com.swayam.demo.opentracing.service;

import java.util.List;

import com.swayam.demo.opentracing.entity.Currency;

public interface CurrencyService {

	Integer addCurrency(Currency currency);

	List<Currency> getAllCurrencies();

	Currency getCurrencyByName(String name);

}

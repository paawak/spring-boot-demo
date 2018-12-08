package com.swayam.demo.springbootdemo.rest.service;

import java.util.List;

import com.swayam.demo.springbootdemo.rest.entity.Currency;

public interface CurrencyService {

	Integer addCurrency(Currency currency);

	List<Currency> getAllCurrencies();

	Currency getCurrencyByName(String name);

}

package com.swayam.demo.springbootdemo.rest.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.swayam.demo.springbootdemo.rest.dao.CurrencyDao;
import com.swayam.demo.springbootdemo.rest.entity.Currency;

@Service
public class CurrencyServiceImpl implements CurrencyService {

	private final CurrencyDao currencyDao;

	public CurrencyServiceImpl(CurrencyDao currencyDao) {
		this.currencyDao = currencyDao;
	}

	@Override
	public Integer addCurrency(Currency currency) {
		return currencyDao.save(currency).getId();
	}

	@Override
	public List<Currency> getAllCurrencies() {
		return currencyDao.findAll();
	}

	@Override
	public Currency getCurrencyByName(String name) {
		return currencyDao.getCurrencyByName(name);
	}

}

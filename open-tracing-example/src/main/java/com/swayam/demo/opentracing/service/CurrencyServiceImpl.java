package com.swayam.demo.opentracing.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.swayam.demo.opentracing.dao.CurrencyDao;
import com.swayam.demo.opentracing.entity.Currency;

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

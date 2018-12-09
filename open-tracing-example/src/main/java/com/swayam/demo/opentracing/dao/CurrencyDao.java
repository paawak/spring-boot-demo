package com.swayam.demo.opentracing.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.swayam.demo.opentracing.entity.Currency;

@Repository
public interface CurrencyDao extends JpaRepository<Currency, Integer> {

	Currency getCurrencyByName(String name);

}

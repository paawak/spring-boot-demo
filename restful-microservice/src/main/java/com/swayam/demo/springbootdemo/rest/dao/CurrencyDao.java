package com.swayam.demo.springbootdemo.rest.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.swayam.demo.springbootdemo.rest.entity.Currency;

@Repository
public interface CurrencyDao extends JpaRepository<Currency, Integer> {

	Currency getCurrencyByName(String name);

}

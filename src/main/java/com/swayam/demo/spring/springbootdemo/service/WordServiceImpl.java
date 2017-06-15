package com.swayam.demo.spring.springbootdemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swayam.demo.spring.springbootdemo.dao.WordDao;

@Service
public class WordServiceImpl implements WordService {

	private final WordDao wordDao;

	@Autowired
	public WordServiceImpl(WordDao wordDao) {
		this.wordDao = wordDao;
	}

	@Transactional
	@Override
	public void save(String word) {
		wordDao.save(word);
	}

}

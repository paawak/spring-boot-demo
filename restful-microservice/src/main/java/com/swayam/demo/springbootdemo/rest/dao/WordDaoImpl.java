package com.swayam.demo.springbootdemo.rest.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class WordDaoImpl implements WordDao {

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public WordDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void save(String word) {
		String sql = "INSERT INTO MY_WORD (word) VALUES (?)";
		jdbcTemplate.update(sql, word);
	}

}

package com.swayam.demo.springbootdemo.reactive.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.swayam.demo.springbootdemo.reactive.model.BankDetail;

@Repository
public class BankDetailDao {

	private final JdbcTemplate jdbcTemplate;

	public BankDetailDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<BankDetail> getBankDetails() {

		return jdbcTemplate.query("select * from bank_details", (ResultSet resultSet, int row) -> {
			return mapResultSet(resultSet);
		});

	}

	private BankDetail mapResultSet(ResultSet resultSet) throws SQLException {
		BankDetail bankDetail = new BankDetail();
		bankDetail.setId(resultSet.getInt("id"));
		bankDetail.setAge(resultSet.getInt("age"));
		bankDetail.setJob(resultSet.getString("job"));
		bankDetail.setMarital(resultSet.getString("marital"));
		bankDetail.setEducation(resultSet.getString("education"));
		bankDetail.setDefaulted(resultSet.getString("defaulted"));
		bankDetail.setBalance(resultSet.getBigDecimal("balance"));
		bankDetail.setHousing(resultSet.getString("housing"));
		bankDetail.setLoan(resultSet.getString("loan"));
		bankDetail.setContact(resultSet.getString("contact"));
		bankDetail.setDay(resultSet.getInt("day"));
		bankDetail.setMonth(resultSet.getString("month"));
		bankDetail.setDuration(resultSet.getInt("duration"));
		bankDetail.setCampaign(resultSet.getInt("campaign"));
		bankDetail.setPdays(resultSet.getInt("pdays"));
		bankDetail.setPrevious(resultSet.getInt("previous"));
		bankDetail.setPoutcome(resultSet.getString("poutcome"));
		bankDetail.setY(resultSet.getString("y"));
		return bankDetail;
	}

}

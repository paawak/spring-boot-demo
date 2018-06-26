package com.swayam.demo.springbootdemo.messaging.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.swayam.demo.springbootdemo.messaging.model.BankDetail;
import com.swayam.demo.springbootdemo.messaging.model.BankDetailSortOrder;
import com.swayam.demo.springbootdemo.messaging.service.pub.QueuePublisher;

@Repository
public class BankDetailDaoImpl implements BankDetailDao {

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public BankDetailDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void publishRawBankDetailsAsync(BankDetailSortOrder bankDetailGroups, QueuePublisher dataPublisher) {

		ResultSetExtractor<Void> resultSetExtractor = (ResultSet resultSet) -> {
			while (resultSet.next()) {
				dataPublisher.publish(mapResultSet(resultSet));
			}

			return null;
		};

		jdbcTemplate.query("select * from bank_details ", resultSetExtractor);

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

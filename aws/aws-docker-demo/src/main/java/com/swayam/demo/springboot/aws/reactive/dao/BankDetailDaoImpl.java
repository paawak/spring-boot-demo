package com.swayam.demo.springboot.aws.reactive.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.swayam.demo.springboot.aws.reactive.model.BankDetail;

import reactor.core.publisher.FluxSink;

@Repository
public class BankDetailDaoImpl implements BankDetailDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(BankDetailDaoImpl.class);

    private static final String BANK_DETAILS_SQL = "select * from bank_details";

    private final JdbcTemplate jdbcTemplate;

    public BankDetailDaoImpl(JdbcTemplate jdbcTemplate) {
	this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<BankDetail> getBankDetails() {

	return jdbcTemplate.query(BANK_DETAILS_SQL, (ResultSet resultSet, int row) -> {
	    return mapResultSet(resultSet);
	});

    }

    @Override
    public void publishBankDetails(FluxSink<BankDetail> fluxSink) {

	jdbcTemplate.query(BANK_DETAILS_SQL, (ResultSet rs) -> {
	    LOGGER.info("start publishing...");
	    int rowCount = 0;
	    while (rs.next()) {

		if (fluxSink.isCancelled()) {
		    LOGGER.info("publishing is cancelled");
		    return null;
		}

		fluxSink.next(mapResultSet(rs));
		if (++rowCount % 2 == 0) {
		    try {
			LOGGER.info("in delay...");
			Thread.sleep(1000);
		    } catch (InterruptedException e) {
			LOGGER.error("error", e);
		    }
		}

	    }
	    LOGGER.info("completed publishing");
	    fluxSink.complete();
	    return null;
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

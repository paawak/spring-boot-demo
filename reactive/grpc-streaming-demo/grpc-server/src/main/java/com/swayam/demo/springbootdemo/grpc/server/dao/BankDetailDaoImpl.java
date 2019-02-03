package com.swayam.demo.springbootdemo.grpc.server.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.swayam.demo.springbootdemo.grpc.shared.proto.BankDetailDto;

import io.grpc.stub.StreamObserver;

@Repository
public class BankDetailDaoImpl implements BankDetailDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(BankDetailDaoImpl.class);

    private static final String BANK_DETAILS_SQL = "select * from bank_details where id <= 10";

    private final JdbcTemplate jdbcTemplate;

    public BankDetailDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void getBankDetailsReactive(StreamObserver<BankDetailDto> responseObserver) {

        jdbcTemplate.query(BANK_DETAILS_SQL, (ResultSet rs) -> {
            LOGGER.info("start publishing...");
            int rowCount = 0;
            while (rs.next()) {

                responseObserver.onNext(mapResultSet(rs));

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
            responseObserver.onCompleted();
            return null;
        });
    }

    private BankDetailDto mapResultSet(ResultSet resultSet) throws SQLException {
        return BankDetailDto.newBuilder().setId(resultSet.getInt("id")).setAge(resultSet.getInt("age")).setJob(resultSet.getString("job"))
                .setMarital(resultSet.getString("marital")).setEducation(resultSet.getString("education")).setDefaulted(resultSet.getString("defaulted"))
                .setBalance(resultSet.getDouble("balance")).setHousing(resultSet.getString("housing")).setLoan(resultSet.getString("loan"))
                .setContact(resultSet.getString("contact")).setDay(resultSet.getInt("day")).setMonth(resultSet.getString("month"))
                .setDuration(resultSet.getInt("duration")).setCampaign(resultSet.getInt("campaign")).setPdays(resultSet.getInt("pdays"))
                .setPrevious(resultSet.getInt("previous")).setPoutcome(resultSet.getString("poutcome")).setY(resultSet.getString("y")).build();
    }

}

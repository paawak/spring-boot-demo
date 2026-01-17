package com.swayam.demo.springbootdemo.testcontainer.cassandra.model;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("bank_detail")
public record BankDetail(

	@PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED) Integer id,

	Integer age,

	String job,

	String marital,

	String education,

	String defaulted,

	float balance,

	String housing,

	String loan,

	String contact,

	Integer day,

	String month,

	Integer duration,

	Integer campaign,

	Integer pdays,

	Integer previous,

	String poutcome,

	String y) {

}

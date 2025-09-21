package com.swayam.demo.springbootdemo.testcontainer.cassandra.model;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.Data;

@Table("bank_detail")
@Data
public class BankDetail {

	@PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
	private Integer id;

	@Column
	private Integer age;

	@Column
	private String job;

	@Column
	private String marital;

	@Column
	private String education;

	@Column
	private String defaulted;

	@Column
	private float balance;

	@Column
	private String housing;

	@Column
	private String loan;

	@Column
	private String contact;

	@Column
	private Integer day;

	@Column
	private String month;

	@Column
	private Integer duration;

	@Column
	private Integer campaign;

	@Column
	private Integer pdays;

	@Column
	private Integer previous;

	@Column
	private String poutcome;

	@Column
	private String y;

}

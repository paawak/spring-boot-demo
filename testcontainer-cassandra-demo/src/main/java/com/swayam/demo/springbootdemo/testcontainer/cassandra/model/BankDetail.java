package com.swayam.demo.springbootdemo.testcontainer.cassandra.model;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("bank_detail")
public record BankDetail(

		@PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED) Integer id,

		@Column Integer age,

		@Column String job,

		@Column String marital,

		@Column String education,

		@Column String defaulted,

		@Column float balance,

		@Column String housing,

		@Column String loan,

		@Column String contact,

		@Column Integer day,

		@Column String month,

		@Column Integer duration,

		@Column Integer campaign,

		@Column Integer pdays,

		@Column Integer previous,

		@Column String poutcome,

		@Column String y) {

}

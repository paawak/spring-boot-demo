package com.swayam.demo.springbootdemo.testcontainer.cassandra.model;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

/**
 * NOTE: the annotation <code>@Column</code> without specifying the value like
 * <code>@Column("age")</code> results in the below exception:
 * 
 * <pre>
 * [dispatcherServlet]    : Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed: java.lang.StringIndexOutOfBoundsException: Index 0 out of bounds for length 0] with root cause

java.lang.StringIndexOutOfBoundsException: Index 0 out of bounds for length 0
at java.base/jdk.internal.util.Preconditions$1.apply(Preconditions.java:55) ~[na:na]
at java.base/jdk.internal.util.Preconditions$1.apply(Preconditions.java:52) ~[na:na]
at java.base/jdk.internal.util.Preconditions$4.apply(Preconditions.java:213) ~[na:na]
at java.base/jdk.internal.util.Preconditions$4.apply(Preconditions.java:210) ~[na:na]
at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:98) ~[na:na]
at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106) ~[na:na]
at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302) ~[na:na]
at java.base/java.lang.String.checkIndex(String.java:4832) ~[na:na]
at java.base/java.lang.StringLatin1.charAt(StringLatin1.java:46) ~[na:na]
at java.base/java.lang.String.charAt(String.java:1555) ~[na:na]
at com.datastax.oss.driver.internal.core.util.Strings.needsDoubleQuotes(Strings.java:100) ~[java-driver-core-4.19.2.jar:4.19.2]
at com.datastax.oss.driver.api.core.CqlIdentifier.fromCql(CqlIdentifier.java:83) ~[java-driver-core-4.19.2.jar:4.19.2]
at org.springframework.data.cassandra.core.mapping.AnnotatedCassandraConstructorProperty.getColumnName(AnnotatedCassandraConstructorProperty.java:60) ~[spring-data-cassandra-5.0.0.jar:5.0.0]
at org.springframework.data.cassandra.core.mapping.CassandraPersistentProperty.getRequiredColumnName(CassandraPersistentProperty.java:67) ~[spring-data-cassandra-5.0.0.jar:5.0.0]
at org.springframework.data.cassandra.core.convert.RowValueProvider.hasProperty(RowValueProvider.java:69) ~[spring-data-cassandra-5.0.0.jar:5.0.0]
at org.springframework.data.cassandra.core.convert.MappingCassandraConverter.getReadValue(MappingCassandraConverter.java:1168) ~[spring-data-cassandra-5.0.0.jar:5.0.0]
at org.springframework.data.cassandra.core.convert.MappingCassandraConverter$CassandraPersistentEntityParameterValueProvider.getParameterValue(MappingCassandraConverter.java:1519) ~[spring-data-cassandra-5.0.0.jar:5.0.0]
at org.springframework.data.mapping.model.ValueExpressionParameterValueProvider.getParameterValue(ValueExpressionParameterValueProvider.java:57) ~[spring-data-commons-4.0.0.jar:4.0.0]
at org.springframework.data.mapping.model.ClassGeneratingEntityInstantiator.extractInvocationArguments(ClassGeneratingEntityInstantiator.java:324) ~[spring-data-commons-4.0.0.jar:4.0.0]
at org.springframework.data.mapping.model.ClassGeneratingEntityInstantiator$EntityInstantiatorAdapter.createInstance(ClassGeneratingEntityInstantiator.java:296) ~[spring-data-commons-4.0.0.jar:4.0.0]
at org.springframework.data.mapping.model.ClassGeneratingEntityInstantiator.createInstance(ClassGeneratingEntityInstantiator.java:95) ~[spring-data-commons-4.0.0.jar:4.0.0]
at org.springframework.data.cassandra.core.convert.MappingCassandraConverter.doReadEntity(MappingCassandraConverter.java:576) ~[spring-data-cassandra-5.0.0.jar:5.0.0]
at org.springframework.data.cassandra.core.convert.MappingCassandraConverter.doReadEntity(MappingCassandraConverter.java:538) ~[spring-data-cassandra-5.0.0.jar:5.0.0]
at org.springframework.data.cassandra.core.convert.MappingCassandraConverter.doReadEntity(MappingCassandraConverter.java:501) ~[spring-data-cassandra-5.0.0.jar:5.0.0]
at org.springframework.data.cassandra.core.convert.MappingCassandraConverter.doReadRow(MappingCassandraConverter.java:481) ~[spring-data-cassandra-5.0.0.jar:5.0.0]
at org.springframework.data.cassandra.core.convert.MappingCassandraConverter.readRow(MappingCassandraConverter.java:477) ~[spring-data-cassandra-5.0.0.jar:5.0.0]
at org.springframework.data.cassandra.core.convert.MappingCassandraConverter.read(MappingCassandraConverter.java:459) ~[spring-data-cassandra-5.0.0.jar:5.0.0]
at org.springframework.data.cassandra.core.convert.MappingCassandraConverter.project(MappingCassandraConverter.java:344) ~[spring-data-cassandra-5.0.0.jar:5.0.0]
at org.springframework.data.cassandra.core.QueryOperations.lambda$getMapper$0(QueryOperations.java:197) ~[spring-data-cassandra-5.0.0.jar:5.0.0]
at org.springframework.data.cassandra.core.QueryOperations.lambda$getRowMapper$0(QueryOperations.java:168) ~[spring-data-cassandra-5.0.0.jar:5.0.0]
at org.springframework.data.cassandra.core.cql.RowMapperResultSetExtractor.extractData(RowMapperResultSetExtractor.java:80) ~[spring-data-cassandra-5.0.0.jar:5.0.0]
at org.springframework.data.cassandra.core.cql.RowMapperResultSetExtractor.extractData(RowMapperResultSetExtractor.java:43) ~[spring-data-cassandra-5.0.0.jar:5.0.0]
at org.springframework.data.cassandra.core.cql.CqlTemplate.query(CqlTemplate.java:405) ~[spring-data-cassandra-5.0.0.jar:5.0.0]
at org.springframework.data.cassandra.core.cql.CqlTemplate.query(CqlTemplate.java:421) ~[spring-data-cassandra-5.0.0.jar:5.0.0]
at org.springframework.data.cassandra.core.CassandraTemplate.doQuery(CassandraTemplate.java:786) ~[spring-data-cassandra-5.0.0.jar:5.0.0]
at org.springframework.data.cassandra.core.QueryOperations$DefaultSelection.select(QueryOperations.java:380) ~[spring-data-cassandra-5.0.0.jar:5.0.0]
at org.springframework.data.cassandra.core.CassandraTemplate.select(CassandraTemplate.java:367) ~[spring-data-cassandra-5.0.0.jar:5.0.0]
at org.springframework.data.cassandra.repository.support.SimpleCassandraRepository.findAll(SimpleCassandraRepository.java:134) ~[spring-data-cassandra-5.0.0.jar:5.0.0]
at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:103) ~[na:na]
at java.base/java.lang.reflect.Method.invoke(Method.java:580) ~[na:na]
at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:359) ~[spring-aop-7.0.1.jar:7.0.1]
 * </pre>
 */
@Table("bank_detail")
public record BankDetail(

	@PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED) Integer id,

	@Column("age") Integer age,

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

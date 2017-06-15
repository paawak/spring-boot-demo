package com.swayam.demo.spring.springbootdemo.config;

import javax.sql.DataSource;

import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DaoConfig {

	@Bean
	public DataSource dataSource(Environment environment) {
		PoolProperties poolProperties = new PoolProperties();
		poolProperties.setUrl(environment.getProperty("jdbc.url"));
		poolProperties.setDriverClassName(environment.getProperty("jdbc.driverClassName"));
		poolProperties.setUsername(environment.getProperty("jdbc.username"));
		poolProperties.setPassword(environment.getProperty("jdbc.password"));
		poolProperties.setJmxEnabled(true);
		poolProperties.setTestWhileIdle(false);
		poolProperties.setTestOnBorrow(true);
		poolProperties.setValidationQuery(environment.getProperty("jdbc.validationQuery"));
		poolProperties.setTestOnReturn(false);
		poolProperties.setValidationInterval(30000);
		poolProperties.setTimeBetweenEvictionRunsMillis(30000);
		poolProperties.setMaxActive(100);
		poolProperties.setInitialSize(10);
		poolProperties.setMaxWait(10000);
		poolProperties.setRemoveAbandonedTimeout(60);
		poolProperties.setMinEvictableIdleTimeMillis(30000);
		poolProperties.setMinIdle(10);
		poolProperties.setLogAbandoned(true);
		poolProperties.setRemoveAbandoned(true);
		poolProperties.setJdbcInterceptors(environment.getProperty("jdbc.interceptors"));

		return new org.apache.tomcat.jdbc.pool.DataSource(poolProperties);
	}

	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

}

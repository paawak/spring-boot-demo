package com.swayam.demo.springbootdemo.testcontainer.cassandra.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.core.cql.session.init.KeyspacePopulator;
import org.springframework.data.cassandra.core.cql.session.init.ResourceKeyspacePopulator;

//@Configuration
public class BankDetailDataLoaderConfiguration extends AbstractCassandraConfiguration {

	private final String keySpaceName;

	public BankDetailDataLoaderConfiguration(@Value("${spring.cassandra.keyspace-name}") String keySpaceName) {
		this.keySpaceName = keySpaceName;
	}

	@Override
	protected KeyspacePopulator keyspacePopulator() {
		return new ResourceKeyspacePopulator(new ClassPathResource("/bank_detail_data.cql"));
	}

	@Override
	protected KeyspacePopulator keyspaceCleaner() {
		return new ResourceKeyspacePopulator(scriptOf("DROP TABLE bank_detail;"));
	}

	@Override
	protected String getKeyspaceName() {
		return keySpaceName;
	}

}

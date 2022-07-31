package com.swayam.demo.springbootdemo.liquibase.service;

import java.io.IOException;
import java.sql.SQLException;

import javax.sql.DataSource;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Service;

import com.swayam.demo.springbootdemo.liquibase.config.ApplicationProperties;

import liquibase.Liquibase;
import liquibase.Scope;
import liquibase.database.DatabaseConnection;
import liquibase.database.jvm.JdbcConnection;
import liquibase.diff.output.DiffOutputControl;
import liquibase.diff.output.StandardObjectChangeFilter;
import liquibase.exception.LiquibaseException;
import liquibase.integration.commandline.CommandLineUtils;
import liquibase.resource.FileSystemResourceAccessor;

/**
 * https://docs.liquibase.com/tools-integrations/maven/commands/maven-generate-changelog.html
 * 
 * @author paawak
 *
 */
@Service
public class BatchedDBExporter {

    public static final String KEY_START_OFFSET = "KEY_START_OFFSET";
    public static final String KEY_SIZE = "KEY_SIZE";

    private static final Logger LOG = LoggerFactory.getLogger(BatchedDBExporter.class);

    private final DataSource dataSource;
    private final JdbcOperations jdbcOperations;
    private final ApplicationProperties applicationProperties;

    public BatchedDBExporter(DataSource dataSource, JdbcOperations jdbcOperations,
	    ApplicationProperties applicationProperties) {
	this.dataSource = dataSource;
	this.jdbcOperations = jdbcOperations;
	this.applicationProperties = applicationProperties;
    }

    public void export() throws LiquibaseException, SQLException, IOException, ParserConfigurationException {
	String tableName = applicationProperties.getTableName();
	String rowCountQuery = "SELECT COUNT(*) FROM " + tableName;
	int rowCount = jdbcOperations.queryForObject(rowCountQuery, Integer.class);
	LOG.info("Found {} rows for the table {}", rowCount, tableName);
	for (int startOffset = 0; startOffset < rowCount; startOffset +=
		applicationProperties.getBatchSize()) {
	    String changelogFile = applicationProperties.getChangeLogDir() + "changeLog_" + tableName + "_"
		    + startOffset + "_" + (startOffset + applicationProperties.getBatchSize()) + ".xml";
	    LOG.info("Generating file {}...", changelogFile);
	    exportSingleBatch(changelogFile, tableName, startOffset, applicationProperties.getBatchSize());
	}
    }

    private void exportSingleBatch(String changelogFile, String tableName, int startOffset, int size)
	    throws IOException, ParserConfigurationException, LiquibaseException, SQLException {
	DatabaseConnection database = new JdbcConnection(dataSource.getConnection());

	try (Liquibase liquibase = new Liquibase(null, new FileSystemResourceAccessor(), database);) {
	    StandardObjectChangeFilter filter =
		    new StandardObjectChangeFilter(StandardObjectChangeFilter.FilterType.INCLUDE, tableName);
	    DiffOutputControl diffOutputControl = new DiffOutputControl(false, false, false, null);
	    diffOutputControl.setObjectChangeFilter(filter);

	    String catalogueName = null;
	    String schemaName = null;
	    String diffTypes = "batcheddata";// if null, it will export schema.
					     // The value of this can also be
					     // *data*
	    String author = "Palash Ray";
	    String context = null;
	    String dataDir = null;// "./target";// set this to null for all data
				  // in xml:
				  // if not null, will create CSV files

	    Scope.getCurrentScope().getUI().set(KEY_START_OFFSET, 0);
	    Scope.getCurrentScope().getUI().set(KEY_SIZE, 30);

	    CommandLineUtils.doGenerateChangeLog(changelogFile, liquibase.getDatabase(), catalogueName,
		    schemaName, diffTypes, author, context, dataDir, diffOutputControl);

	}
    }

}

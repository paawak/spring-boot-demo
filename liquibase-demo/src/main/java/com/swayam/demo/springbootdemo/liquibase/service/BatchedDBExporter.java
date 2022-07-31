package com.swayam.demo.springbootdemo.liquibase.service;

import java.io.IOException;
import java.sql.SQLException;

import javax.sql.DataSource;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.stereotype.Service;

import liquibase.Liquibase;
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

    private final DataSource dataSource;

    public BatchedDBExporter(DataSource dataSource) {
	this.dataSource = dataSource;
    }

    public void export() throws LiquibaseException, SQLException, IOException, ParserConfigurationException {
	DatabaseConnection database = new JdbcConnection(dataSource.getConnection());

	String tableName = "country";

	try (Liquibase liquibase = new Liquibase(null, new FileSystemResourceAccessor(), database);) {
	    StandardObjectChangeFilter filter =
		    new StandardObjectChangeFilter(StandardObjectChangeFilter.FilterType.INCLUDE, tableName);
	    DiffOutputControl diffOutputControl = new DiffOutputControl(false, false, false, null);
	    diffOutputControl.setObjectChangeFilter(filter);

	    String changelogFile = "./target/changelog.xml";
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

	    CommandLineUtils.doGenerateChangeLog(changelogFile, liquibase.getDatabase(), catalogueName,
		    schemaName, diffTypes, author, context, dataDir, diffOutputControl);

	}
    }

}

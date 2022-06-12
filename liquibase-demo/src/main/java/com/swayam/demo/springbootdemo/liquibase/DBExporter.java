package com.swayam.demo.springbootdemo.liquibase;

import java.io.IOException;
import java.sql.SQLException;

import javax.annotation.PostConstruct;
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
public class DBExporter {

    private final DataSource dataSource;

    public DBExporter(DataSource dataSource) {
	this.dataSource = dataSource;
    }

    @PostConstruct
    public void export() throws LiquibaseException, SQLException, IOException, ParserConfigurationException {
	DatabaseConnection database = new JdbcConnection(dataSource.getConnection());

	try (Liquibase liquibase = new Liquibase(null, new FileSystemResourceAccessor(), database);) {

	    DiffOutputControl diffOutputControl = new DiffOutputControl(false, false, false, null);
	    diffOutputControl.setObjectChangeFilter(
		    new StandardObjectChangeFilter(StandardObjectChangeFilter.FilterType.INCLUDE, "country"));

	    String diffTypes = "data";// if null, it will export schema

	    CommandLineUtils.doGenerateChangeLog("./target/changelog.xml", liquibase.getDatabase(), "", "",
		    diffTypes, "Palash Ray", "", null, diffOutputControl);

	}
    }

}

package com.swayam.demo.springbootdemo.liquibase;

import java.io.IOException;
import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.stereotype.Service;

import liquibase.CatalogAndSchema;
import liquibase.Liquibase;
import liquibase.database.DatabaseConnection;
import liquibase.database.jvm.JdbcConnection;
import liquibase.diff.output.DiffOutputControl;
import liquibase.diff.output.changelog.DiffToChangeLog;
import liquibase.exception.LiquibaseException;
import liquibase.resource.FileSystemResourceAccessor;

@Service
public class DBExporter {

    private final DataSource dataSource;

    public DBExporter(DataSource dataSource) {
	this.dataSource = dataSource;
    }

    @PostConstruct
    public void export() throws LiquibaseException, SQLException, IOException, ParserConfigurationException {
	DatabaseConnection database = new JdbcConnection(dataSource.getConnection());
	try (Liquibase liquibase =
		new Liquibase("./target/changelog.xml", new FileSystemResourceAccessor(), database);) {
	    liquibase.generateChangeLog(new CatalogAndSchema(null, "public"),
		    new DiffToChangeLog(new DiffOutputControl()), System.out);
	}
    }

}

package com.swayam.demo.springbootdemo.liquibase.plugin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.swayam.demo.springbootdemo.liquibase.service.query.BatchedDataQuery;
import com.swayam.demo.springbootdemo.liquibase.service.query.PostgresBatchedDataQuery;

import liquibase.GlobalConfiguration;
import liquibase.change.Change;
import liquibase.change.ColumnConfig;
import liquibase.change.core.InsertDataChange;
import liquibase.database.Database;
import liquibase.database.core.InformixDatabase;
import liquibase.database.core.PostgresDatabase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.diff.output.DiffOutputControl;
import liquibase.diff.output.changelog.ChangeGeneratorChain;
import liquibase.diff.output.changelog.core.MissingDataChangeGenerator;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.statement.DatabaseFunction;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Data;
import liquibase.structure.core.Table;
import liquibase.util.JdbcUtil;

public class BatchingMissingDataChangeGenerator extends MissingDataChangeGenerator {

    @Override
    public int getPriority(Class<? extends DatabaseObject> objectType, Database database) {
	if (BatchedData.class.isAssignableFrom(objectType)) {
	    return PRIORITY_DEFAULT;
	}
	return PRIORITY_NONE;
    }

    @Override
    public Change[] fixMissing(DatabaseObject missingObject, DiffOutputControl outputControl,
	    Database referenceDatabase, Database comparisionDatabase, ChangeGeneratorChain chain) {
	System.out.println("*********** BatchingMissingDataChangeGenerator.fixMissing()");
	BatchedDataQuery batchedDataQuery = getBatchedDataQuery(referenceDatabase);
	Statement stmt = null;
	ResultSet rs = null;
	try {
	    Data data = (Data) missingObject;

	    Table table = data.getTable();
	    if (referenceDatabase.isLiquibaseObject(table)) {
		return null;
	    }

	    String sql = batchedDataQuery.getSqlQuery(referenceDatabase, table, 0, 20);
	    stmt = ((JdbcConnection) referenceDatabase.getConnection())
		    .createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	    stmt.setFetchSize(1000);
	    rs = stmt.executeQuery(sql);

	    List<String> columnNames = new ArrayList<>();
	    for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
		columnNames.add(rs.getMetaData().getColumnName(i + 1));
	    }

	    List<Change> changes = new ArrayList<>();
	    while (rs.next()) {
		InsertDataChange change = new InsertDataChange();
		if (outputControl.getIncludeCatalog()) {
		    change.setCatalogName(table.getSchema().getCatalogName());
		}
		if (outputControl.getIncludeSchema()) {
		    change.setSchemaName(table.getSchema().getName());
		}
		change.setTableName(table.getName());

		// loop over all columns for this row
		for (int i = 0; i < columnNames.size(); i++) {
		    ColumnConfig column = new ColumnConfig();
		    column.setName(columnNames.get(i));

		    Object value = JdbcUtil.getResultSetValue(rs, i + 1);
		    if (value == null) {
			column.setValue(null);
		    } else if (value instanceof Number) {
			column.setValueNumeric((Number) value);
		    } else if (value instanceof Boolean) {
			column.setValueBoolean((Boolean) value);
		    } else if (value instanceof Date) {
			column.setValueDate((Date) value);
		    } else if (value instanceof byte[]) {
			if (referenceDatabase instanceof InformixDatabase) {
			    column.setValue(new String((byte[]) value,
				    GlobalConfiguration.OUTPUT_FILE_ENCODING.getCurrentValue()));
			}
			column.setValueComputed(new DatabaseFunction("UNSUPPORTED FOR DIFF: BINARY DATA"));
		    } else { // fall back to simple string
			column.setValue(value.toString());
		    }

		    change.addColumn(column);

		}

		// for each row, add a new change
		// (there will be one group per table)
		changes.add(change);
	    }

	    return changes.toArray(new Change[changes.size()]);
	} catch (Exception e) {
	    throw new UnexpectedLiquibaseException(e);
	} finally {
	    if (rs != null) {
		try {
		    rs.close();
		} catch (SQLException ignore) {
		}
	    }
	    if (stmt != null) {
		try {
		    stmt.close();
		} catch (SQLException ignore) {
		}
	    }
	}
    }

    private BatchedDataQuery getBatchedDataQuery(Database db) {
	if (db instanceof PostgresDatabase) {
	    return new PostgresBatchedDataQuery();
	}
	throw new UnsupportedOperationException("Batching is not supported for " + db.getDatabaseProductName()
		+ ". Please create an implementation of the " + BatchedDataQuery.class);
    }

}

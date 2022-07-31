package com.swayam.demo.springbootdemo.liquibase;

import liquibase.exception.DatabaseException;
import liquibase.snapshot.DatabaseSnapshot;
import liquibase.snapshot.InvalidExampleException;
import liquibase.snapshot.jvm.JdbcSnapshotGenerator;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Table;

public class BatchedDataSnapshotGenerator extends JdbcSnapshotGenerator {

    public BatchedDataSnapshotGenerator() {
	super(BatchedData.class, new Class[] { Table.class });
    }

    @Override
    protected DatabaseObject snapshotObject(DatabaseObject example, DatabaseSnapshot snapshot)
	    throws DatabaseException, InvalidExampleException {
	return example;
    }

    @Override
    protected void addTo(DatabaseObject foundObject, DatabaseSnapshot snapshot)
	    throws DatabaseException, InvalidExampleException {
	if (!snapshot.getSnapshotControl().shouldInclude(BatchedData.class)) {
	    return;
	}
	if (foundObject instanceof Table) {
	    Table table = (Table) foundObject;
	    try {

		BatchedData exampleData = new BatchedData().setTable(table);
		table.setAttribute("batcheddata", exampleData);
	    } catch (Exception e) {
		throw new DatabaseException(e);
	    }
	}
    }

}

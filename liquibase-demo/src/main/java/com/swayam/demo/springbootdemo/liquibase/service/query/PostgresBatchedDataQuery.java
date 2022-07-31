package com.swayam.demo.springbootdemo.liquibase.service.query;

import liquibase.database.Database;
import liquibase.structure.core.Table;

public class PostgresBatchedDataQuery implements BatchedDataQuery {

    @Override
    public String getSqlQuery(Database database, Table table, int start, int size) {
	String sql =
		"SELECT * FROM "
			+ database.escapeTableName(table.getSchema().getCatalogName(),
				table.getSchema().getName(), table.getName())
			+ " OFFSET " + start + " LIMIT " + size;
	return sql;
    }

}

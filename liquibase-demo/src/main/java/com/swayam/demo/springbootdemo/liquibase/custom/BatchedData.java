package com.swayam.demo.springbootdemo.liquibase.custom;

import liquibase.structure.core.Data;
import liquibase.structure.core.Table;

public class BatchedData extends Data {

    @Override
    public BatchedData setTable(Table table) {
	setAttribute("table", table);

	return this;
    }

    @Override
    public BatchedData setName(String name) {
	Table table = getTable();
	if (table == null) {
	    setTable(new Table().setName(name));
	} else {
	    table.setName(name);
	}

	return this;
    }

}

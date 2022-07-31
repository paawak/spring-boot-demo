package com.swayam.demo.springbootdemo.liquibase.service.query;

import liquibase.database.Database;
import liquibase.structure.core.Table;

public interface BatchedDataQuery {

    String getSqlQuery(Database database, Table table, int startOffset, int size);

}

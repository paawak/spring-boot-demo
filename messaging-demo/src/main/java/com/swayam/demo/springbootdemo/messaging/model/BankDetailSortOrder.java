package com.swayam.demo.springbootdemo.messaging.model;

public enum BankDetailSortOrder {

    JOB("job"), MARITAL_STATUS("marital"), EDUCATION("education");

    private final String columnName;

    private BankDetailSortOrder(String columnName) {
	this.columnName = columnName;
    }

    public String getColumnName() {
	return columnName;
    }

}

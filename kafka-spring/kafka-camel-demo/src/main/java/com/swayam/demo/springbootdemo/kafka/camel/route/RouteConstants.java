package com.swayam.demo.springbootdemo.kafka.camel.route;

public class RouteConstants {

    public static final String AGGREGATION_CHANNEL = "direct:bank-details-aggr";
    public static final String TYPE_HEADER = "__TypeId__";
    public static final String COMPLETE_JOB_AGGREGATION_COMMAND = "COMPLETE_JOB_AGGREGATION";

    private RouteConstants() {

    }

}

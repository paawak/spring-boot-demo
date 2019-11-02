package com.swayam.demo.springbootdemo.kafka.camel.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.kafka.KafkaConstants;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Service;

import com.swayam.demo.springbootdemo.kafkadto.BankDetail;
import com.swayam.demo.springbootdemo.kafkadto.CompletionSignal;
import com.swayam.demo.springbootdemo.kafkadto.JobCount;

@Service
public class BankDetailAggregatorByJob extends RouteBuilder {

    private final NamedParameterJdbcOperations jdbcTemplate;

    public BankDetailAggregatorByJob(NamedParameterJdbcOperations jdbcTemplate) {
	this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void configure() {
	from("kafka:bank-details?brokers=localhost:9092" + "&autoOffsetReset=earliest"
		+ "&autoCommitEnable=true" + "&groupId=bank-detail-camel-consumer")
			.routeId(BankDetailAggregatorByJob.class.getSimpleName() + "_to_channel")
			.to(RouteConstants.AGGREGATION_CHANNEL);

	from(RouteConstants.AGGREGATION_CHANNEL)
		.routeId(BankDetailAggregatorByJob.class.getSimpleName() + "_aggregation").choice()
		.when(simple("${header." + RouteConstants.TYPE_HEADER + "} == '"
			+ BankDetail.class.getName() + "'"))
		.unmarshal().json(JsonLibrary.Jackson, BankDetail.class).process(exchange -> {
		    BankDetail bankDetail = exchange.getIn().getBody(BankDetail.class);
		    exchange.getIn().setBody(toJobCount(bankDetail), JobCount.class);
		}).when(simple("${header." + RouteConstants.TYPE_HEADER + "} == '"
			+ CompletionSignal.class.getName() + "'"))
		.process(exchange -> {
		    exchange.getIn().setBody(new JobCount(), JobCount.class);
		    exchange.getIn().setHeader(RouteConstants.COMPLETE_JOB_AGGREGATION_COMMAND,
			    Boolean.TRUE);
		}).end()
		.aggregate(header(KafkaConstants.KEY),
			new BankDetailAggregationStrategy(jdbcTemplate))
		.log("${headers[" + KafkaConstants.KEY + "]} : ${body}");
    }

    private JobCount toJobCount(BankDetail bankDetail) {
	JobCount jobCount = new JobCount();

	switch (bankDetail.getJob()) {
	case JobCount.ADMIN:
	    jobCount.setAdminCount(1);
	    break;
	case JobCount.BLUE_COLLAR:
	    jobCount.setBlueCollarCount(1);
	    break;
	case JobCount.ENTREPRENEUR:
	    jobCount.setEntrepreneurCount(1);
	    break;
	case JobCount.HOUSEMAID:
	    jobCount.setHouseMaidCount(1);
	    break;
	case JobCount.MANAGEMENT:
	    jobCount.setManagementCount(1);
	    break;
	case JobCount.RETIRED:
	    jobCount.setRetiredCount(1);
	    break;
	case JobCount.SELF_EMPLOYED:
	    jobCount.setSelfEmployedCount(1);
	    break;
	case JobCount.SERVICES:
	    jobCount.setServicesCount(1);
	    break;
	case JobCount.STUDENT:
	    jobCount.setStudentCount(1);
	    break;
	case JobCount.TECHNICIAN:
	    jobCount.setTechnicianCount(1);
	    break;
	case JobCount.UNEMPLOYED:
	    jobCount.setUnemployedCount(1);
	    break;
	case JobCount.UNKNOWN:
	    jobCount.setUnknownCount(1);
	    break;
	default:
	    throw new UnsupportedOperationException(
		    "Unknown Job Code: " + bankDetail.getJob() + " for id: " + bankDetail.getId());
	}

	return jobCount;
    }

}
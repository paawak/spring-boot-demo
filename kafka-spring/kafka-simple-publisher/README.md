# About

This is a simple Kafka Publisher which publishes BankDetails on to the Topic *bank-details*.

## Trigger publishing of Bank Details

	curl "http://localhost:8090/kafka/send"

## H2 Database Console

	http://localhost:8090/h2-console
	
Jdbc URL: *jdbc:h2:mem:bank_details*	

User name/password: *sa*	

# Kafka Reference
## Starting Kafka Locally

	bin/zookeeper-server-start.sh config/zookeeper.properties
	bin/kafka-server-start.sh config/server.properties
	
## Creating a Topic

	bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic bank-details
	
## Listing all Topics

	bin/kafka-topics.sh --list --bootstrap-server localhost:9092
		
## Deleting a Topic

	bin/kafka-topics.sh --zookeeper localhost:2181 --delete --topic bank-details		
	
## Inspect messages on a Topic

	bin/kafka-console-consumer.sh --bootstrap-server localhost:9092     \
		--topic bank-details     --from-beginning     \
		--formatter kafka.tools.DefaultMessageFormatter   \
		--property print.key=true     --property print.value=true     \
		--property key.deserializer=org.apache.kafka.common.serialization.StringDeserializer     \
		--property value.deserializer=org.apache.kafka.common.serialization.StringDeserializer
		
## Further reference
* [Official Kafka documentation](https://kafka.apache.org/quickstart)

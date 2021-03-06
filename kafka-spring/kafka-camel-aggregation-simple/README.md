# About

This is a demo to show how messages can be aggregated using Kafka/Camel/Spring

# Running the app

## Start Kafka

	cd $KAFKA_HOME

	bin/zookeeper-server-start.sh config/zookeeper.properties > zookeepr.log &
	
	bin/kafka-server-start.sh config/server.properties > kafka.log &
	
## Start the application	

You can run another instance by changing the port

	-Dserver.port=8080

## Publish messages on Kafka

Start the **KafkaSimplePublisherApplication** and the use the below command to publish messages on Kafka

	curl "http://localhost:8090/kafka/publish"

## Shutdown the application	gracefully

	curl -X POST http://localhost:8080/actuator/shutdown
	
# Docker
## Building docker image

	mvn clean package
	
## Running Docker image

	docker run -it -p 8080:8080 -e server.port=8080 -e spring.profiles.active=default paawak/kafka-camel-aggregation-simple:latest
			
# Miscellaneous
## Allow docker host to access MySql

	CREATE USER 'root'@'172.17.0.%' IDENTIFIED BY 'root123';
	GRANT ALL PRIVILEGES ON *.* TO 'root'@'172.17.0.%' WITH GRANT OPTION;
	FLUSH PRIVILEGES;			
	
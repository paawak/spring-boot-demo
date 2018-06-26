# About

Demo project for Spring Boot with a Message Broker

# Building and pushing a Docker image
	
	mvn clean install -P docker
	
# Staring the docker image

	docker run -it -p 8080:8080 -e spring.profiles.active=default,rabbitmq paawak/messaging-demo:latest

# Starting with RabbitMQ:

	-Dspring.profiles.active=default,rabbitmq
	
# Starting with ActiveMQ:

	-Dspring.profiles.active=default,activemq	

# Starting RabbitMQ in Docker

docker run -it -p 5673:5672 -e RABBITMQ_DEFAULT_USER=admin -e RABBITMQ_DEFAULT_PASS=admin123 rabbitmq:3.7.6

# To trigger pub-sub of bank details

1. From UI: http://localhost:8080/

2. By making a GET request to: http://localhost:8080/bank-data/publish/JOB

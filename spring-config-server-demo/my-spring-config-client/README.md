# About

This is a client of a [Spring Config Server](https://cloud.spring.io/spring-cloud-config/). This does not store any configuration locally, but gets it from http://localhost:8888/config-server-proxy/

# Testing

The below command should print-out property from cloud-config

	curl -s -X GET "http://localhost:8080/my-spring-config-client" 


# Running a RabbitMQ on Docker

	docker run -it -d -p 5672:5672 -p 15672:15672 -e RABBITMQ_DEFAULT_USER=my_mq_user -e RABBITMQ_DEFAULT_PASS=mq_secret_57*key rabbitmq:3.7.2-management-alpine 
	
	
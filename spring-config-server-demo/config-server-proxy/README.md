# About

This is a [Spring Config Server] (https://cloud.spring.io/spring-cloud-config/) demo. This acts as a proxy to a Github project containing the actual properties to be served.

# Running a RabbitMQ on Docker

	docker run -it -d -p 5672:5672 -p 15672:15672 -e RABBITMQ_DEFAULT_USER=my_mq_user -e RABBITMQ_DEFAULT_PASS=mq_secret_57*key rabbitmq:3.7.2-management-alpine 

# Fetching application properties

	curl -u "my_user:MySecret&23" -s -X GET "http://localhost:8888/config-server-proxy/my-spring-config-client-default.yml"
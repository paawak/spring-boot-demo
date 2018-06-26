# About

Demo project for Spring Boot with a Message Broker

# Starting RabbitMQ in Docker

docker run -it -p 5672:5672 -e RABBITMQ_DEFAULT_USER=admin -e RABBITMQ_DEFAULT_PASS=admin123 rabbitmq:3.7.6

# To trigger pub-sub of bank details

GET request: http://localhost:8080/bank-data/publish/JOB

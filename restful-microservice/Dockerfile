FROM openjdk:8u151-jre-alpine3.7
MAINTAINER Palash Ray <paawak@gmail.com>
CMD mkdir /logs
ADD target/restful-microservice.jar //
ENTRYPOINT ["java", "-jar", "/restful-microservice.jar"]

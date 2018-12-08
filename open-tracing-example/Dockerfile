FROM openjdk:8u191-jdk-alpine3.7
MAINTAINER Palash Ray <paawak@gmail.com>
ENV APP_HOME=/usr/local/app
ENV PROJECT_NAME=open-tracing-example
ENV APP_JAR_NAME=${PROJECT_NAME}.jar
CMD mkdir -p $APP_HOME/jfr
WORKDIR $APP_HOME
ADD target/${APP_JAR_NAME} .
CMD java -XX:+FlightRecorder -XX:StartFlightRecording=duration=60s,filename=jfr/${PROJECT_NAME}.jfr -jar ${APP_JAR_NAME}

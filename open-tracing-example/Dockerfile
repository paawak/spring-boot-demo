FROM openjdk:11.0.1-jre-slim-sid
MAINTAINER Palash Ray <paawak@gmail.com>
ENV APP_HOME=/usr/local/app
ENV APP_JAR_NAME=open-tracing-example.jar
CMD mkdir -p $APP_HOME
WORKDIR $APP_HOME
ADD target/${APP_JAR_NAME} .
CMD java -jar ${APP_JAR_NAME}

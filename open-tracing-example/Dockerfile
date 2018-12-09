FROM openjdk:11.0.1-sid
MAINTAINER Palash Ray <paawak@gmail.com>

ENV JFR_PROFILE_SOURCE=https://hg.openjdk.java.net/jdk/jdk11/raw-file/76072a077ee1/src/jdk.jfr/share/conf/jfr
ENV JFR_PROFILE_HOME=/docker-java-home/lib/jfr
ENV APP_HOME=/usr/local/app
ENV PROJECT_NAME=open-tracing-example
ENV LOG_HOME=/usr/local/logs
ENV JFR_LOG_HOME=${LOG_HOME}/jfr
ENV APP_JAR_NAME=${PROJECT_NAME}.jar

ENV JFR_OPTS="-XX:+FlightRecorder -XX:StartFlightRecording=duration=60s,filename=$JFR_LOG_HOME/${PROJECT_NAME}.jfr"

# Workaround for https://bugs.debian.org/cgi-bin/bugreport.cgi?bug=910804
RUN mkdir -p $JFR_PROFILE_HOME && \
    wget $JFR_PROFILE_SOURCE/default.jfc \
      -O $JFR_PROFILE_HOME/default.jfc && \
    wget $JFR_PROFILE_SOURCE/profile.jfc \
      -O $JFR_PROFILE_HOME/profile.jfc
      
RUN mkdir -p $APP_HOME && \
    mkdir -p $LOG_HOME && \
    mkdir -p $JFR_LOG_HOME


WORKDIR $APP_HOME
ADD target/${APP_JAR_NAME} .
CMD java ${JFR_OPTS} -Dspring.profiles.active=docker -jar ${APP_JAR_NAME}

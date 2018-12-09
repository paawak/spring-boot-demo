FROM openjdk:11.0.1-sid
MAINTAINER Palash Ray <paawak@gmail.com>

ENV APP_HOME=/usr/local/app
ENV PROJECT_NAME=open-tracing-example
ENV LOG_HOME=/usr/local/logs
ENV JFR_LOG_HOME=${LOG_HOME}/jfr
ENV APP_JAR_NAME=${PROJECT_NAME}.jar

# Workaround for https://bugs.debian.org/cgi-bin/bugreport.cgi?bug=910804
RUN mkdir -p /docker-java-home/lib/jfr && \
    wget https://hg.openjdk.java.net/jdk/jdk11/raw-file/76072a077ee1/src/jdk.jfr/share/conf/jfr/default.jfc \
      -O /docker-java-home/lib/jfr/default.jfc && \
    wget https://hg.openjdk.java.net/jdk/jdk11/raw-file/76072a077ee1/src/jdk.jfr/share/conf/jfr/profile.jfc \
      -O /docker-java-home/lib/jfr/profile.jfc && \
    mkdir -p $APP_HOME && \
    mkdir -p $LOG_HOME && \
    mkdir -p $JFR_LOG_HOME


WORKDIR $APP_HOME
ADD target/${APP_JAR_NAME} .
CMD java -XX:+FlightRecorder -XX:StartFlightRecording=duration=60s,filename=$JFR_LOG_HOME/${PROJECT_NAME}.jfr -jar ${APP_JAR_NAME}

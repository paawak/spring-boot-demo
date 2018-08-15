#!/bin/bash

EMBEDDED_TOMCAT_JARS_HOME=/kaaj/source/tomcat/output/embed
EMBEDDED_TOMCAT_JARS_SOURCE_HOME=/kaaj/source/tomcat/output/embed-src-jars
EMBEDDED_TOMCAT_VERSION=9.0.12-DEV-SNAPSHOT
EMBEDDED_TOMCAT_ARTIFACTS=(tomcat-embed-core tomcat-embed-el tomcat-embed-websocket)

for artifactId in ${EMBEDDED_TOMCAT_ARTIFACTS[*]}
do

    echo "Deploying $artifactId..."
    mvn install:install-file -DgroupId=org.apache.tomcat.embed \
      -DartifactId=$artifactId \
      -Dversion=$EMBEDDED_TOMCAT_VERSION \
      -Dpackaging=jar \
      -Dfile=$EMBEDDED_TOMCAT_JARS_HOME/$artifactId.jar

    mvn install:install-file -DgroupId=org.apache.tomcat.embed \
      -DartifactId=$artifactId \
      -Dversion=$EMBEDDED_TOMCAT_VERSION \
      -Dpackaging=java-source \
      -Dfile=$EMBEDDED_TOMCAT_JARS_SOURCE_HOME/$artifactId-src.jar
done

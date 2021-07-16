FROM openjdk:11.0.8-jre
MAINTAINER Palash Ray <paawak@gmail.com>
RUN mkdir /tesseract-temp-images
ADD target/porua-ocr-service.jar //
ENTRYPOINT ["java", "-jar", "/porua-ocr-service.jar"]

# About

Demo for a Reactive Netty/Spring Boot Application within Docker. Can be accessed here: <http://localhost:8080/index.html>

# How build docker image

mvn clean package -P docker

# How upload docker image to docker hub

mvn clean install -P docker

# How to run

## Running in Local

    java -Dspring.profiles.active=local -jar target/spring-boot-docker-demo.jar

## Running in Docker

    docker run -it -p 8080:8080    \
    paawak/spring-boot-docker-demo:latest
				
# Sources
		
<https://github.com/paawak/porua/tree/master/tesseract-ocr-rest>

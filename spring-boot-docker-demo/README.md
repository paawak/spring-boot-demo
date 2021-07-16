# About

Demo for a Reactive Netty/Spring Boot Application within Docker. Can be accessed here: <http://localhost:8080/index.html>

# How build docker image

mvn clean package -P docker

# How upload docker image to docker hub

mvn clean install -P docker

# How to run

## Running in Local

    java -Dspring.profiles.active=local -jar target/porua-ocr-service.jar

## Running in Docker

    docker run -it -p 8080:8080    \
    -v /kaaj/installs/tesseract/tessdata_best-4.0.0:/tesseract/tessdata    \
    -v /kaaj/source/porua/tesseract-ocr-rest/images:/tesseract-temp-images   \
    -e spring.profiles.active=container     \
    paawak/porua-ocr-service:latest
				
# Sources
		
<https://github.com/paawak/porua/tree/master/tesseract-ocr-rest>

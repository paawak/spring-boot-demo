FROM openjdk:11.0.8-jre
LABEL author.name="Palash Ray"
LABEL author.email="paawak@gmail.com"
ADD target/bank-service.jar //
EXPOSE 5000
ENTRYPOINT ["java", "-jar", "/bank-service.jar"]

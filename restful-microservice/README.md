# Building a Docker image
	mvn clean package -P docker.fabric8

# Running standalone with Java Flight Recorder with OpenJDK 11

	java -XX:+FlightRecorder -XX:StartFlightRecording=duration=60s,filename=target/restful-microservice.jfr -jar target/restful-microservice.jar 



# Running this application in Docker

	docker run -it -d -p 8080:8080 paawak/restful-microservice:latest
	
# To add a new Currency
	curl -H "Content-Type: application/json" -X POST -d @src/test/resources/json/save_new_currency_request.json http://localhost:8080/restful-microservice/currency
	
# To list all Currencies
	curl -X GET http://localhost:8080/restful-microservice/currency	

# To know flyway status
	mvn flyway:info -Dflyway.url=jdbc:postgresql://localhost:5432/spring_boot_rest -Dflyway.user=postgres -Dflyway.password=postgres

# Starting SonarQube

The first time:

		docker run -d --name sonarqube -p 9000:9000 -p 9092:9092 sonarqube:6.7.1
		
Subsequently:
		
		docker start 	sonarqube
		
To login to Sonarqube with username/password : admin/admin at: http://localhost:9000
		
		
# Starting Jenkins

The first time:

		docker run -d --name jenkins -p 8100:8080 -p 50000:50000 -v /kaaj/java/jenkins/jenkins-docker-home:/var/jenkins_home jenkins/jenkins:2.60.3
				
Subsequently:
		
		docker start jenkins
		
Access Jenkins at: http://localhost:8100
		
		
		

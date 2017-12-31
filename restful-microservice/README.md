## To add a new Currency
	curl -H "Content-Type: application/json" -X POST -d @src/test/resources/json/save_new_currency_request.json http://localhost:8080/restful-microservice/currency
	
## To list all Currencies
	curl -X GET http://localhost:8080/restful-microservice/currency	


## Starting SonarQube
		docker run -d --name sonarqube -p 9000:9000 -p 9092:9092 sonarqube
		
## Starting Jenkins

		docker run -d -p 8100:8080 -p 50000:50000 -v /kaaj/java/jenkins/jenkins-docker-home:/var/jenkins_home jenkins/jenkins:latest
				

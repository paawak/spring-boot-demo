## Starting SonarQube
		docker run -d --name sonarqube -p 9000:9000 -p 9092:9092 sonarqube
		
## Starting Jenkins

		docker run -d -p 8100:8080 -p 50000:50000 -v /kaaj/java/jenkins/jenkins-docker-home:/var/jenkins_home jenkins/jenkins:latest
				
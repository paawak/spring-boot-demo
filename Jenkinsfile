pipeline {
    
    agent {
		label 'master'        
    }
    
    stages {
        stage('Build') {
            steps {
                withSonarQubeEnv('MySonarQubeLocal') {
                	dir('code/spring-boot-demo') {
	                    sh "/var/jenkins_home/apache-maven-3.5.0/bin/mvn clean install sonar:sonar -P sonar"
	                    script {
	                        def sonarProps = readFile encoding: 'utf-8', file: 'target/sonar/report-task.txt'
	                        echo "sonarProps: " + sonarProps
	                        java.util.Properties props = new java.util.Properties()
	                        props.load(new java.io.ByteArrayInputStream(sonarProps.toBytes()))   
	                        echo "props: " + props
	                    }
	                }
                }
            }
        }
    }


    
}

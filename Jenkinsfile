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
	                        def ceTaskUrl = 'ceTaskUrl'
	                        sonarProps.split('\n').each { line ->
	                            if (line.startsWith(ceTaskUrl)) {
	                                 env.SONAR_CE_TASK_URL = line.substring(ceTaskUrl.length() + 1)
	                                 echo "env.SONAR_CE_TASK_URL: " + env.SONAR_CE_TASK_URL  
	                            }
	                            
	                            if (line.startsWith('serverUrl')) {
	                            	 def sonarServerUrl = line.split('=')[1]
	                            	 if (!sonarServerUrl.endsWith('/')) {
	                            	      sonarServerUrl += '/'
	                            	 }

	                                 env.SONAR_SERVER_URL = sonarServerUrl
	                                 echo "env.SONAR_SERVER_URL: " + env.SONAR_SERVER_URL  
	                            }
	                        }
	                    }
	                }
                }
            }
        }
        
        stage('Quality Gate') {
            steps {
            	dir('code/spring-boot-demo') {
	                script {
	                    sleep time: 3000, unit: 'MILLISECONDS'
	                    timeout(time: 1, unit: 'MINUTES') {
	                        waitUntil {
	                            def jsonOutputFile = 'target/sonar/ceTask.json'
	                            sh 'curl $SONAR_CE_TASK_URL -o ' + jsonOutputFile
	                            def jsonOutputFileContents = readFile encoding: 'utf-8', file: jsonOutputFile
	                            def ceTask = new groovy.json.JsonSlurper().parseText(jsonOutputFileContents)
	                            env.SONAR_ANALYSIS_ID = ceTask['task']['analysisId']
	                            return 'SUCCESS'.equals(ceTask['task']['status'])
	                        }
	                        def qualityGateUrl = env.SONAR_SERVER_URL + 'api/qualitygates/project_status?analysisId=' + env.SONAR_ANALYSIS_ID
	                        echo "qualityGateUrl: " + qualityGateUrl
	                        def qualityGateJsonFile = 'target/sonar/qualityGate.json'
	                        sh 'curl $qualityGateUrl -o ' + qualityGateJsonFile
	                        def qualityGateJsonFileContents = readFile encoding: 'utf-8', file: qualityGateJsonFile
	                        def qualityGateJson = new groovy.json.JsonSlurper().parseText(qualityGateJsonFileContents)
	                        echo 'qualityGateJson: ' + qualityGateJson
	                        if ("ERROR".equals(qualityGateJson['projectStatus']['status'])) {
	                               error "Quality Gate Failure"
	                        }
	                        echo "Quality Gate Success"
	                    }
	                }
                }
            }
        }

    }
    
}

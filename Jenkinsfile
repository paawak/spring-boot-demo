pipeline {
    
    agent {
		label 'master'        
    }
    
    stages {
        stage('Build') {
            steps {
                withSonarQubeEnv('MySonarQubeLocal') {
                    sh "mvn clean install sonar:sonar -P sonar"
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

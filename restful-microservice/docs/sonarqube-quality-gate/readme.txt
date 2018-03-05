Run the Maven command:
	mvn clean install sonar:sonar -P sonar

This will create the below file:
	target/sonar/report-task.txt

There are the below 2 urls that has to be read from here:
	1. serverUrl=http://192.168.1.4:9000
	2. ceTaskUrl=http://192.168.1.4:9000/api/ce/task?id=AWE3eRSZAEMe8tTgpicn

Read the response from the ceTaskUrl using curl, and save it to a file ceTask.json:
	curl http://192.168.1.4:9000/api/ce/task?id=AWE3eRSZAEMe8tTgpicn -o ceTask.json
	
The element we are interested in is task.analysisId:
	"analysisId": "AWE3eRcyxJqMzJgr501D"	
	
We need to read the response of the url: $serverUrl/api/qualitygates/project_status?analysisId=$analysisId
	curl http://192.168.1.4:9000/api/qualitygates/project_status?analysisId=AWE3eRcyxJqMzJgr501D -o qualityGate.json
	
If the Json value projectStatus.status is ERROR, the project has failed QualityGate.

		
# About

This is a [Spring Config Server](https://cloud.spring.io/spring-cloud-config/) demo. This acts as a proxy to a Github project containing the actual properties to be served.

# Fetching application properties

	curl -u "my_user:MySecret&23" -s -X GET "http://localhost:8888/config-server-proxy/my-spring-config-client-default.yml" 

# Running a RabbitMQ on Docker

	docker run -it -d -p 5672:5672 -p 15672:15672 -e RABBITMQ_DEFAULT_USER=my_mq_user -e RABBITMQ_DEFAULT_PASS=mq_secret_57*key rabbitmq:3.7.2-management-alpine 
	
# Creating a self-signed certificate

	keytool -genkey -noprompt -trustcacerts -keyalg RSA -alias config-server.palashray.com -dname "CN=Palash Ray, OU=Demo, O=Swayam, L=Bangalore, ST=Karnataka, C=IN" -keypass Red123 -storepass Yellow98 -keystore certs/config-server-keystore.jks 	

# Encrypting a password

	curl -u "my_user:MySecret&23" -s -X POST "http://localhost:8888/config-server-proxy/encrypt" --data-urlencode my_passwd
	
# Decrypting a password

	curl -u "my_user:MySecret&23" -s -X POST "http://localhost:8888/config-server-proxy/decrypt" --data-urlencode my_passwd
	
	
# About

This is a [Spring Config Server] (https://cloud.spring.io/spring-cloud-config/) demo. This acts as a proxy to a Github project containing the actual properties to be served.

# Fetching application properties

	curl -u "my_user:MySecret&23" -s -X GET "http://localhost:8888/config-server-proxy/my-spring-config-client-default.yml"
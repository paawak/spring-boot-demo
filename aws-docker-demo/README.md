# About

Demo for a Reactive Netty/Spring Boot Application within Docker. Can be accessed here: <http://localhost:8080/index.html>

# How build docker image

    mvn clean package -P docker

# How upload docker image to docker hub

    mvn clean install -P docker

# How to run

## Running in Local

    java -Dspring.profiles.active=local -jar target/aws-docker-demo.jar

## Running in Docker

    docker run -it -p 8080:5000    \
    paawak/aws-docker-demo:latest
    
## Running on AWS Elastic Beanstalk
For the application to run on Beanstalk without any addition Beanstalk-specific configuration files, you need to listen to Docker port *5000*. Also, you would need to *expose* this port in your Docker file:

    EXPOSE 5000

If you do that, you are pretty much all set. You can use the below commands to deploy your application on to AWS:

    eb init -p docker  aws-docker-demo
    eb create demo-with-docker-env
    
To open the browser:

    eb open
    
To terminate:
        
        eb terminate demo-with-docker-env
        
This will upload zip and upload all the files within that directory. 

You can also create an application by simply uploading the docker compose file:

```yaml
version: '3'

services:
    aws-docker-demo:
        image: paawak/aws-docker-demo:latest
        ports:
          - "80:5000"
        environment:
          - spring.profiles.active=default
```


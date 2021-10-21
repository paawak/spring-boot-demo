# About

Demo for a Reactive Netty/Spring Boot Application within Docker. Can be accessed here: <http://localhost:5000/index.html>. This can also be deployed to AWS Elastic Beanstalk through command line or by uploading the *docker-compose.yml*

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
But note that you need to first upload this image to the Docker Hub. These are the commands:

    docker login
    docker push paawak/aws-docker-demo:latest
        
## Running on Minikube
### Display Dashboard

    minikube dashboard

### Deploying an application
The below steps are taken from <https://kubernetes.io/docs/tasks/run-application/run-stateless-application-deployment/>

1. Start Minikube

    minikube start --nodes 2 -p aws-docker-demo
    
1. Check the status of the nodes
    
    minikube status -p aws-docker-demo        

1. Create a Deployment based on the YAML file:

    kubectl apply -f kubernetes/minikube/application-deployment.yml

1. Display information about the Deployment:

    kubectl describe deployment aws-docker-demo-deployment
        
1. List the Pods created by the deployment:

    kubectl get pods -l app=aws-docker-demo
    
1. Display information about a Pod:

    kubectl describe pod <pod-name>
    
1. Deleting a deployment:

    kubectl delete deployment aws-docker-demo-deployment
    
### Exposing a deployed application
The below steps are taken from <https://kubernetes.io/docs/concepts/services-networking/service/> and <https://kubernetes.io/docs/tutorials/stateless-application/expose-external-ip-address/>

#### Using the expose command

    kubectl expose deployment aws-docker-demo-deployment --type=LoadBalancer --name=aws-docker-demo-service

1. To get external IPs    
Use any of the below commands:    

    minikube service aws-docker-demo-service
    
    minikube ip
    
1. Deleting a service:

    kubectl delete svc aws-docker-demo-service    

#### Deploying a Service

    kubectl apply -f kubernetes/minikube/application-service.yml

1. Display information about the Deployment:

    kubectl describe svc aws-docker-demo-service
    
1. Get the Endpoints

    kubectl get ep aws-docker-demo-service
    
1. Get the NodePort

    kubectl get svc aws-docker-demo-service -o yaml | grep nodePort -C 10
    
1. Get the IP Address

    kubectl get nodes -o yaml | grep ExternalIP -C 1
    
### ConfigMaps
#### Creating from yaml file

    kubectl create configmap dev-config --from-file=src/main/resources/application.yml
    
#### Verify that configmap has been created

    kubectl get configmaps dev-config -o yaml   
    
#### Using ConfigMap

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: aws-docker-demo-deployment
spec:
  selector:
    matchLabels:
      app: aws-docker-demo
  replicas: 2 # tells deployment to run 2 pods matching the template
  template:
    metadata:
      labels:
        app: aws-docker-demo
    spec:
      containers:
      - name: aws-docker-demo
        image: paawak/aws-docker-demo:latest
        ports:
        - containerPort: 5000
        env:
          - name: APPLICATION_CONFIG 
            valueFrom:
              configMapKeyRef:
                name: dev-config
                key: application.yml  
```
    
#### Reference    
1. <https://kubernetes.io/docs/concepts/configuration/overview/>    
1. <https://kubernetes.io/docs/tasks/configure-pod-container/configure-pod-configmap/>    
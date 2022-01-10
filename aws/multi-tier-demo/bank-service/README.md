# About

Demo for a Reactive Netty/Spring Boot Application within Docker. Can be accessed here: <http://localhost:5000/index.html>. This can also be deployed to AWS Elastic Beanstalk through command line or by uploading the *docker-compose.yml*

# Useful links

<https://skaffold.dev/docs/pipeline-stages/init/>

<https://github.com/kubernetes-sigs/kustomize>

<https://kubectl.docs.kubernetes.io/installation/kustomize/>

<https://kustomize.io/>

# How build docker image

    mvn clean package -P docker

# How upload docker image to docker hub

    mvn clean install -P docker

# How to run

## Running in Local

    java -jar target/bank-service.jar

## Running in Docker

    docker run -it -p 5000:5000    \
    paawak/bank-service:latest
            
## Running on Minikube
### Deploying an application
The below steps are taken from <https://kubernetes.io/docs/tasks/run-application/run-stateless-application-deployment/>

1. Start Minikube

    minikube start --nodes 2 -p bank-service
    
    
1. Check the status of the nodes
    
    minikube status -p bank-service        
    

1. Create a Deployment based on the YAML file:

    kubectl apply -k overlays/dev/

1. Display information about the Deployment:

    kubectl describe deployment bank-service-dev
        
1. List the Pods created by the deployment:

    kubectl get pods -l app=bank-service
    
1. Display information about a Pod:

    kubectl describe pod <pod-name>
    
1. Deleting a deployment:

    kubectl delete deployment bank-service-dev
    
### Exposing a deployed application
The below steps are taken from <https://kubernetes.io/docs/concepts/services-networking/service/> and <https://kubernetes.io/docs/tutorials/stateless-application/expose-external-ip-address/>

1. To get external IPs    
Use any of the below commands:    

    minikube service bank-service-loadbalancer-dev
    
    minikube ip
    
1. Deleting a service:

    kubectl delete svc bank-service-service    

#### Deploying a Service

    kubectl apply -f kubernetes/minikube/application-service.yml

1. Display information about the Deployment:

    kubectl describe svc bank-service-service
    
1. Get the Endpoints

    kubectl get ep bank-service-service
    
1. Get the NodePort

    kubectl get svc bank-service-service -o yaml | grep nodePort -C 10
    
1. Get the IP Address

    kubectl get nodes -o yaml | grep ExternalIP -C 1
    
                    
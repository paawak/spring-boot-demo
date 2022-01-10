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

- Start Minikube

    minikube start --nodes 2
        
- Create the namespace
    
    kubectl create namespace paawak-bank-app        
    
- Create a Deployment and LoadBalancers through Kustomize

    kubectl apply -k overlays/dev/

- Display information about the Deployment:

    kubectl describe deployment bank-service-dev
        
- List the Pods created by the deployment:

    kubectl get pods -l app=bank-service
    
- Display information about a Pod:

    kubectl describe pod <pod-name>
    
- To get external IPs    
Use any of the below commands:    

    minikube service bank-service-loadbalancer-dev
    
    minikube ip    
    
- Deleting a deployment:

    kubectl delete deployment bank-service-dev
    
- Stopping Minikube
    
    minikube ip    
    

    
                    
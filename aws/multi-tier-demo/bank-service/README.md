# About

Demo for a Reactive Netty/Spring Boot Application within Docker. Can be accessed here: <http://localhost:5000/index.html>. This can also be deployed to AWS Elastic Beanstalk through command line or by uploading the *docker-compose.yml*

# Useful links

<https://skaffold.dev/docs/pipeline-stages/init/>

<https://github.com/kubernetes-sigs/kustomize>

<https://kubectl.docs.kubernetes.io/installation/kustomize/>

<https://kustomize.io/>

<https://kubernetes.io/docs/tasks/manage-kubernetes-objects/kustomization/>

# How build docker image

We use Jib Maven Plugin: 

<https://github.com/GoogleContainerTools/jib>

<https://github.com/GoogleContainerTools/jib/tree/master/jib-maven-plugin>

    mvn compile jib:build

# How to run

## Running on Kubernetes

With Skaffold

    skaffold run --tail

## Running in Local

    java -jar target/bank-service.jar

## Running in Docker

    docker run -it -p 5000:5000    \
    paawak/bank-service:latest
            
## Save entire docker image as a tar

    docker create --name latest-bank-service  paawak/bank-service:latest false
    docker export latest-bank-service  -o bank-service.tar            
            
## Running on Minikube
### Deploying an application
The below steps are taken from <https://kubernetes.io/docs/tasks/run-application/run-stateless-application-deployment/>

- Start Minikube

    minikube start --nodes 2
    
- Minikube Dashboard

    minikube dashboard
        
- Create the namespace
    
    kubectl create namespace paawak-bank-app        
    
    kubectl config set-context --current --namespace=paawak-bank-app
    
- Create a Deployment and LoadBalancers through Kustomize

    kubectl apply -k kubernetes/overlays/dev/

- Display information about the Deployment:

    kubectl describe deployment bank-service-dev
        
- List the Pods created by the deployment:

    kubectl get pods -l app=bank-service
    
- Display information about a Pod:

    kubectl describe pod <pod-name>
    
- Log into a shell of running pod

    kubectl exec -it bank-service-65464f59ff-rq7jj -n paawak-bank-app -- /bin/bash    
    
- To get external IPs    
Use any of the below commands:    

    minikube service bank-service-loadbalancer-dev -n paawak-bank-app
    
    minikube ip    
    
- Deleting all deployments:

    kubectl delete -k overlays/dev/
    
- Stopping Minikube
    
    minikube stop    
    

    
                    
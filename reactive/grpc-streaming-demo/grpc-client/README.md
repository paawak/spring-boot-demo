# About
This project demonstrates various Java-based grpc clients.

## Simple grpc client
A simple grpc client has a main method and consumes data stream from a grpc server. It is the simplest, bare-bones grpc client in Java.

The main class to run is: **GrpcSimpleClientApplication**


## HTTP based grpc client
This is a Spring Boot based HTTP (Netty) Server. It has a Reactive, WebFlux based REST end point. This end point is a conduit for reading from a grpc server stream. There is also a simple web page for demo: <http://localhost:9090/index.html>. It is based on a similar [end to end Spring Boot Reactive demo](https://github.com/paawak/spring-boot-demo/tree/master/reactive/reactive-demo-with-netty). You can also refer to my [blog entry](http://palashray.com/end-to-end-implementation-of-reactive-streams-from-server-to-client/) for more details. 

The main class to run is: **GrpcRestClientApplication**

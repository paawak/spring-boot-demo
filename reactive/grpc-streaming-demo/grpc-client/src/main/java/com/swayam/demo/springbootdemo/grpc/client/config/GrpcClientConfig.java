package com.swayam.demo.springbootdemo.grpc.client.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

@Configuration
public class GrpcClientConfig {

    @Bean(destroyMethod = "shutdown")
    public ManagedChannel managedChannel(@Value("${grpc.server.host}") String host, @Value("${grpc.server.port}") int port) {
        return ManagedChannelBuilder.forAddress(host, port).usePlaintext(true).build();
    }

}

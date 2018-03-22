package com.swayam.demo.springbootdemo.grpc;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.swayam.demo.springbootdemo.grpc.controller.BankDetailStreamer;

import io.grpc.Server;
import io.grpc.ServerBuilder;

@SpringBootApplication
public class GrpcDemoApplication {

    private static final Logger logger = LoggerFactory.getLogger(GrpcDemoApplication.class);

    @Autowired
    private BankDetailStreamer bankDetailStreamer;

    private Server server;

    private void start() throws IOException {
	/* The port on which the server should run */
	int port = 50051;
	server = ServerBuilder.forPort(port).addService(bankDetailStreamer).build().start();
	logger.info("Server started, listening on " + port);
	Runtime.getRuntime().addShutdownHook(new Thread() {
	    @Override
	    public void run() {
		// Use stderr here since the logger may have been reset by its
		// JVM shutdown hook.
		System.err.println("*** shutting down gRPC server since JVM is shutting down");
		GrpcDemoApplication.this.stop();
		System.err.println("*** server shut down");
	    }
	});
    }

    private void stop() {
	if (server != null) {
	    server.shutdown();
	}
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon
     * threads.
     */
    private void blockUntilShutdown() throws InterruptedException {
	if (server != null) {
	    server.awaitTermination();
	}
    }

    public static void main(String[] args) throws IOException, InterruptedException {
	GrpcDemoApplication server = new GrpcDemoApplication();
	server.start();
	server.blockUntilShutdown();
	// SpringApplication.run(GrpcDemoApplication.class, args);
    }
}

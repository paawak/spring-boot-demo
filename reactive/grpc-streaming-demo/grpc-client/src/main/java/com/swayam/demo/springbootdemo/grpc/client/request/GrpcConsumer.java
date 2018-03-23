package com.swayam.demo.springbootdemo.grpc.client.request;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.swayam.demo.springbootdemo.grpc.shared.proto.BankDetailDto;
import com.swayam.demo.springbootdemo.grpc.shared.proto.BankDetailRequest;
import com.swayam.demo.springbootdemo.grpc.shared.proto.BankDetailStreamerGrpc;
import com.swayam.demo.springbootdemo.grpc.shared.proto.BankDetailStreamerGrpc.BankDetailStreamerStub;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.ClientCallStreamObserver;
import io.grpc.stub.ClientResponseObserver;

public class GrpcConsumer {

    private static final Logger logger = LoggerFactory.getLogger(GrpcConsumer.class);

    public static void main(String[] args) throws InterruptedException {
	final CountDownLatch done = new CountDownLatch(1);

	// Create a channel and a stub
	ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051).usePlaintext(true).build();
	BankDetailStreamerStub stub = BankDetailStreamerGrpc.newStub(channel);

	// When using manual flow-control and back-pressure on the client, the
	// ClientResponseObserver handles both
	// request and response streams.
	ClientResponseObserver<BankDetailRequest, BankDetailDto> clientResponseObserver = new ClientResponseObserver<BankDetailRequest, BankDetailDto>() {

	    @Override
	    public void beforeStart(final ClientCallStreamObserver<BankDetailRequest> requestStream) {

	    }

	    @Override
	    public void onNext(BankDetailDto bankDetailDto) {
		logger.info("bankDetailDto: {}", bankDetailDto);
	    }

	    @Override
	    public void onError(Throwable t) {
		logger.error("error occurred", t);
		done.countDown();
	    }

	    @Override
	    public void onCompleted() {
		logger.info("All Done");
		done.countDown();
	    }
	};

	stub.streamBankDetails(BankDetailRequest.newBuilder().build(), clientResponseObserver);

	done.await();

	channel.shutdown();
	channel.awaitTermination(1, TimeUnit.SECONDS);
    }

}

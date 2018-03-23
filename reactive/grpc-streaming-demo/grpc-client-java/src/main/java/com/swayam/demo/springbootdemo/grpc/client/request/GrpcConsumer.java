package com.swayam.demo.springbootdemo.grpc.client.request;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.swayam.demo.springbootdemo.grpc.proto.BankDetailDto;
import com.swayam.demo.springbootdemo.grpc.proto.BankDetailRequest;
import com.swayam.demo.springbootdemo.grpc.proto.BankDetailStreamerGrpc;
import com.swayam.demo.springbootdemo.grpc.proto.BankDetailStreamerGrpc.BankDetailStreamerStub;

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

	    ClientCallStreamObserver<BankDetailRequest> requestStream;

	    @Override
	    public void beforeStart(final ClientCallStreamObserver<BankDetailRequest> requestStream) {
		this.requestStream = requestStream;
		// Set up manual flow control for the response stream. It feels
		// backwards to configure the response
		// stream's flow control using the request stream's observer,
		// but this is the way it is.
		requestStream.disableAutoInboundFlowControl();

		// Set up a back-pressure-aware producer for the request stream.
		// The onReadyHandler will be invoked
		// when the consuming side has enough buffer space to receive
		// more messages.
		//
		// Messages are serialized into a transport-specific transmit
		// buffer. Depending on the size of this buffer,
		// MANY messages may be buffered, however, they haven't yet been
		// sent to the server. The server must call
		// request() to pull a buffered message from the client.
		//
		// Note: the onReadyHandler's invocation is serialized on the
		// same thread pool as the incoming
		// StreamObserver'sonNext(), onError(), and onComplete()
		// handlers. Blocking the onReadyHandler will prevent
		// additional messages from being processed by the incoming
		// StreamObserver. The onReadyHandler must return
		// in a timely manor or else message processing throughput will
		// suffer.
		requestStream.setOnReadyHandler(new Runnable() {

		    @Override
		    public void run() {
			// Start generating values from where we left off on a
			// non-gRPC thread.
			if (requestStream.isReady()) {
			    logger.info("requesting... ");
			    BankDetailRequest request = BankDetailRequest.newBuilder().build();
			    requestStream.onNext(request);
			    // Signal completion if there is nothing left to
			    // send.
			    requestStream.onCompleted();
			}
		    }
		});
	    }

	    @Override
	    public void onNext(BankDetailDto bankDetailDto) {
		logger.info("bankDetailDto: {}", bankDetailDto);
		// Signal the sender to send one message.
		requestStream.request(1);
	    }

	    @Override
	    public void onError(Throwable t) {
		t.printStackTrace();
		done.countDown();
	    }

	    @Override
	    public void onCompleted() {
		logger.info("All Done");
		done.countDown();
	    }
	};

	// Note: clientResponseObserver is handling both request and response
	// stream processing.
	stub.streamBankDetails(BankDetailRequest.newBuilder().build(), clientResponseObserver);

	done.await();

	channel.shutdown();
	channel.awaitTermination(1, TimeUnit.SECONDS);
    }

}

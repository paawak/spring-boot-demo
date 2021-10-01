package com.swayam.demo.springbootdemo.reactive;

import java.util.concurrent.CountDownLatch;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import com.swayam.demo.springboot.aws.reactive.model.BankDetail;

import reactor.core.publisher.Flux;

class BankDetailControllerIT {

    private static final String REACTIVE_URL = "http://localhost:8080/bank-item";

    @Test
    void testWebClient() throws InterruptedException {

	CountDownLatch countDownLatch = new CountDownLatch(1);

	WebClient webClient = WebClient.builder().baseUrl(REACTIVE_URL).build();
	Flux<BankDetail> bankDetailFlux = webClient.get().uri("/reactive").header("accept", MediaType.TEXT_EVENT_STREAM_VALUE).retrieve().bodyToFlux(BankDetail.class);
	bankDetailFlux.subscribe((BankDetail bankDetail) -> {
	    System.out.println(bankDetail);
	}, (Throwable t) -> {
	    countDownLatch.countDown();
	}, () -> {
	    countDownLatch.countDown();
	});

	countDownLatch.await();
    }

}

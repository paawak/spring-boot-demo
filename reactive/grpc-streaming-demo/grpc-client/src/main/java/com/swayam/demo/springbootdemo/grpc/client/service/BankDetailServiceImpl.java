package com.swayam.demo.springbootdemo.grpc.client.service;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.swayam.demo.springbootdemo.grpc.client.model.BankDetailForJson;
import com.swayam.demo.springbootdemo.grpc.shared.proto.BankDetailDto;
import com.swayam.demo.springbootdemo.grpc.shared.proto.BankDetailRequest;
import com.swayam.demo.springbootdemo.grpc.shared.proto.BankDetailStreamerGrpc;
import com.swayam.demo.springbootdemo.grpc.shared.proto.BankDetailStreamerGrpc.BankDetailStreamerStub;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.ClientCallStreamObserver;
import io.grpc.stub.ClientResponseObserver;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

@Service
public class BankDetailServiceImpl implements BankDetailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BankDetailServiceImpl.class);

    @Override
    public Flux<BankDetailForJson> getBankDetailsReactive() {

        return Flux.create((FluxSink<BankDetailForJson> fluxSink) -> {

            final CountDownLatch done = new CountDownLatch(1);

            ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051).usePlaintext(true).build();
            BankDetailStreamerStub stub = BankDetailStreamerGrpc.newStub(channel);

            ClientResponseObserver<BankDetailRequest, BankDetailDto> clientResponseObserver = new ClientResponseObserver<BankDetailRequest, BankDetailDto>() {

                @Override
                public void beforeStart(final ClientCallStreamObserver<BankDetailRequest> requestStream) {

                }

                @Override
                public void onNext(BankDetailDto bankDetailDto) {
                    LOGGER.info("bankDetailDto: {}", bankDetailDto);
                    fluxSink.next(adapt(bankDetailDto));
                }

                @Override
                public void onError(Throwable t) {
                    LOGGER.error("error occurred", t);
                    fluxSink.error(t);
                    done.countDown();
                }

                @Override
                public void onCompleted() {
                    LOGGER.info("All Done");
                    fluxSink.complete();
                    done.countDown();
                }
            };

            stub.streamBankDetails(BankDetailRequest.newBuilder().build(), clientResponseObserver);

            try {
                done.await();
            } catch (InterruptedException e) {
                LOGGER.error("error occurred", e);
            }

            channel.shutdown();
            try {
                channel.awaitTermination(1, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                LOGGER.error("error occurred", e);
            }

        });

    }

    private BankDetailForJson adapt(BankDetailDto resultSet) {
        BankDetailForJson bankDetail = new BankDetailForJson();
        bankDetail.setId(resultSet.getId());
        bankDetail.setAge(resultSet.getAge());
        bankDetail.setJob(resultSet.getJob());
        bankDetail.setMarital(resultSet.getMarital());
        bankDetail.setEducation(resultSet.getEducation());
        bankDetail.setDefaulted(resultSet.getDefaulted());
        bankDetail.setBalance(resultSet.getBalance());
        bankDetail.setHousing(resultSet.getHousing());
        bankDetail.setLoan(resultSet.getLoan());
        bankDetail.setContact(resultSet.getContact());
        bankDetail.setDay(resultSet.getDay());
        bankDetail.setMonth(resultSet.getMonth());
        bankDetail.setDuration(resultSet.getDuration());
        bankDetail.setCampaign(resultSet.getCampaign());
        bankDetail.setPdays(resultSet.getPdays());
        bankDetail.setPrevious(resultSet.getPrevious());
        bankDetail.setPoutcome(resultSet.getPoutcome());
        bankDetail.setY(resultSet.getY());
        return bankDetail;
    }

}

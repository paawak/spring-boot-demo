package com.swayam.demo.springbootdemo.grpc.client.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.swayam.demo.springbootdemo.grpc.client.dto.HttpFriendlyBankDetail;
import com.swayam.demo.springbootdemo.grpc.shared.proto.BankDetailDto;
import com.swayam.demo.springbootdemo.grpc.shared.proto.BankDetailRequest;
import com.swayam.demo.springbootdemo.grpc.shared.proto.BankDetailStreamerGrpc;
import com.swayam.demo.springbootdemo.grpc.shared.proto.BankDetailStreamerGrpc.BankDetailStreamerStub;

import io.grpc.ManagedChannel;
import io.grpc.stub.ClientCallStreamObserver;
import io.grpc.stub.ClientResponseObserver;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

@Service
public class BankDetailServiceImpl implements BankDetailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BankDetailServiceImpl.class);

    private final ManagedChannel channel;

    public BankDetailServiceImpl(ManagedChannel channel) {
        this.channel = channel;
    }

    @Override
    public Flux<HttpFriendlyBankDetail> getBankDetailsReactive() {

        return Flux.create((FluxSink<HttpFriendlyBankDetail> fluxSink) -> {

            BankDetailStreamerStub stub = BankDetailStreamerGrpc.newStub(channel);

            ClientResponseObserver<BankDetailRequest, BankDetailDto> clientResponseObserver = new ClientResponseObserver<BankDetailRequest, BankDetailDto>() {

                @Override
                public void beforeStart(final ClientCallStreamObserver<BankDetailRequest> requestStream) {

                }

                @Override
                public void onNext(BankDetailDto bankDetailDto) {

                    if (fluxSink.isCancelled()) {
                        LOGGER.info("publishing is cancelled");
                        return;
                    }

                    LOGGER.info("bankDetailDto: {}", bankDetailDto);
                    fluxSink.next(adapt(bankDetailDto));
                }

                @Override
                public void onError(Throwable t) {
                    LOGGER.error("error occurred", t);
                    fluxSink.error(t);
                }

                @Override
                public void onCompleted() {
                    LOGGER.info("All Done");
                    fluxSink.complete();
                }
            };

            stub.streamBankDetails(BankDetailRequest.newBuilder().build(), clientResponseObserver);

        });

    }

    private HttpFriendlyBankDetail adapt(BankDetailDto resultSet) {
        HttpFriendlyBankDetail bankDetail = new HttpFriendlyBankDetail();
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

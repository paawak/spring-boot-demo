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
import io.grpc.stub.StreamObserver;
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
            StreamObserver<BankDetailDto> clientResponseObserver = createObservable(fluxSink);
            stub.streamBankDetails(BankDetailRequest.newBuilder().build(), clientResponseObserver);
        });

    }

    private StreamObserver<BankDetailDto> createObservable(FluxSink<HttpFriendlyBankDetail> fluxSink) {
        return new ClientResponseObserver<BankDetailRequest, BankDetailDto>() {

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
    }

    private HttpFriendlyBankDetail adapt(BankDetailDto dto) {
        HttpFriendlyBankDetail bankDetail = new HttpFriendlyBankDetail();
        bankDetail.setId(dto.getId());
        bankDetail.setAge(dto.getAge());
        bankDetail.setJob(dto.getJob());
        bankDetail.setMarital(dto.getMarital());
        bankDetail.setEducation(dto.getEducation());
        bankDetail.setDefaulted(dto.getDefaulted());
        bankDetail.setBalance(dto.getBalance());
        bankDetail.setHousing(dto.getHousing());
        bankDetail.setLoan(dto.getLoan());
        bankDetail.setContact(dto.getContact());
        bankDetail.setDay(dto.getDay());
        bankDetail.setMonth(dto.getMonth());
        bankDetail.setDuration(dto.getDuration());
        bankDetail.setCampaign(dto.getCampaign());
        bankDetail.setPdays(dto.getPdays());
        bankDetail.setPrevious(dto.getPrevious());
        bankDetail.setPoutcome(dto.getPoutcome());
        bankDetail.setY(dto.getY());
        return bankDetail;
    }

}

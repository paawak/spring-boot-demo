package com.swayam.demo.springbootdemo.kafkastream.processor;

import java.util.ArrayList;
import java.util.List;

import org.apache.kafka.streams.kstream.Aggregator;
import org.apache.kafka.streams.kstream.Initializer;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.binder.kafka.streams.annotations.KafkaStreamsProcessor;
import org.springframework.messaging.handler.annotation.SendTo;

import com.swayam.demo.springbootdemo.kafkadto.BankDetail;

@EnableBinding(KafkaStreamsProcessor.class)
public class BankDetailsProcessor {

    public static final String INPUT_TOPIC = "input";
    public static final String OUTPUT_TOPIC = "output";
    public static final int WINDOW_SIZE_SECOND = 10_000;

    @StreamListener(INPUT_TOPIC)
    @SendTo(OUTPUT_TOPIC)
    public KStream<String, List<BankDetail>> processBankDetails(KStream<String, BankDetail> bankDetailStream) {

        Initializer<List<BankDetail>> initializer = ArrayList::new;
        Aggregator<String, BankDetail, List<BankDetail>> aggregator = (key, bankDetail, bankDetailPartialList) -> {
            bankDetailPartialList.add(bankDetail);
            return bankDetailPartialList;
        };
        return bankDetailStream.groupBy((key, bankDetail) -> bankDetail.getJob()).aggregate(initializer, aggregator).toStream();

        // input.flatMapValues(value ->
        // Arrays.asList(value.toLowerCase().split("\\W+"))).map((key, value) ->
        // new KeyValue<>(value, value))
        // .groupByKey(Serialized.with(Serdes.String(),
        // Serdes.String())).windowedBy(TimeWindows.of(WINDOW_SIZE_SECOND *
        // 1000))
        // .count(Materialized.as("WordCounts-1")).toStream()
        // .map((key, value) -> new KeyValue<>(null, new WordCount(key.key(),
        // value, new Date(key.window().start()), new
        // Date(key.window().end()))));
    }
}
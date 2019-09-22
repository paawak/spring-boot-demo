package com.swayam.demo.springbootdemo.processor;

import java.util.Arrays;
import java.util.Date;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Serialized;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.binder.kafka.streams.annotations.KafkaStreamsProcessor;
import org.springframework.messaging.handler.annotation.SendTo;

@EnableBinding(KafkaStreamsProcessor.class)
public class WordCountProcessor {

    public static final String INPUT_TOPIC = "input";
    public static final String OUTPUT_TOPIC = "output";
    public static final int WINDOW_SIZE_SECOND = 10_000;

    @StreamListener(INPUT_TOPIC)
    @SendTo(OUTPUT_TOPIC)
    public KStream<Bytes, WordCount> process(KStream<Bytes, String> input) {

        return input.flatMapValues(value -> Arrays.asList(value.toLowerCase().split("\\W+"))).map((key, value) -> new KeyValue<>(value, value))
                .groupByKey(Serialized.with(Serdes.String(), Serdes.String())).windowedBy(TimeWindows.of(WINDOW_SIZE_SECOND * 1000))
                .count(Materialized.as("WordCounts-1")).toStream()
                .map((key, value) -> new KeyValue<>(null, new WordCount(key.key(), value, new Date(key.window().start()), new Date(key.window().end()))));
    }
}
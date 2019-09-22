package com.swayam.demo.springbootdemo.kafkastream.processor;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Serialized;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;

import com.swayam.demo.springbootdemo.kafkastream.processor.WordCountKTable.KTableProcessor;

@EnableBinding(KTableProcessor.class)
public class WordCountKTable {

    public static final String INPUT_TOPIC = "kTableInput";
    public static final String OUTPUT_TOPIC = "kTableOutput";
    public static final int WINDOW_SIZE_SECOND = 10_000;

    @StreamListener(INPUT_TOPIC)
    @SendTo(OUTPUT_TOPIC)
    public KStream<String, Long> process1(KTable<Bytes, String> input) {

        return input.groupBy((key, value) -> {
            return new KeyValue<>(value, value);
        }, Serialized.with(Serdes.String(), Serdes.String())).count().toStream();
    }

    public interface KTableProcessor {

        @Input("kTableInput")
        KTable<?, ?> input();

        @Output("kTableOutput")
        KStream<?, ?> output();
    }

}
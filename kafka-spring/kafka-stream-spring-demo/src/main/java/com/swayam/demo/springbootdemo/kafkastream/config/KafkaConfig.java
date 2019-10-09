package com.swayam.demo.springbootdemo.kafkastream.config;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.swayam.demo.springbootdemo.kafkadto.BankDetail;

//@Configuration
public class KafkaConfig {

    @Autowired
    private KafkaProperties kafkaProperties;

    @Bean
    public ConsumerFactory<String, BankDetail> consumerFactory() {
        Map<String, Object> props = kafkaProperties.buildConsumerProperties();
        // props.put(JsonDeserializer.KEY_DEFAULT_TYPE, "com.example.MyKey");
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, BankDetail.class.getName());
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, BankDetail> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, BankDetail> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

}

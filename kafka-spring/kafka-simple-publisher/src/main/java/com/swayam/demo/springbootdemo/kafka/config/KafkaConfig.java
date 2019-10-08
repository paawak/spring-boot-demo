package com.swayam.demo.springbootdemo.kafka.config;

import java.util.Map;

import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
public class KafkaConfig {

    @Bean
    public ProducerFactory<Object, Object> producerFactory(KafkaProperties properties) {
        Map<String, Object> props = properties.buildProducerProperties();
        JsonSerializer<Object> valueSerializer = new JsonSerializer<>();
        // do not have type info
        valueSerializer.setAddTypeInfo(false);
        DefaultKafkaProducerFactory<Object, Object> pf = new DefaultKafkaProducerFactory(props, new StringSerializer(), valueSerializer);
        return pf;
    }

}

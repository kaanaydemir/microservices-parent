package com.kaanaydemir.orderservice.kafka.config;

import com.kaanaydemir.avro.OrderPlacedEvent;
import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Value("${spring.kafka.producer.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${schema.url}")
    private String schemaRegistry;


    public Map<String, Object> producerConfig() {
        Map<String, Object> properties = new HashMap<> ();
        properties.put (ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServers);
        properties.put (ProducerConfig.ACKS_CONFIG,"1");
        properties.put (ProducerConfig.RETRIES_CONFIG,"10");
        properties.put (ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName ());
        properties.put (ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class.getName ());
        properties.put (AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistry);

        return properties;
    }

    @Bean
    public ProducerFactory<String, OrderPlacedEvent> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate(ProducerFactory<String, OrderPlacedEvent> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }
}

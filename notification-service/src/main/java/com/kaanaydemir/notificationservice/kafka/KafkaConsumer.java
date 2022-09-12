package com.kaanaydemir.notificationservice.kafka;

import com.kaanaydemir.avro.OrderPlacedEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @KafkaListener(topics = "notification",
                   containerFactory = "kafkaListenerContainerFactory")
    void listener(ConsumerRecord<String, OrderPlacedEvent> record) {
        String key = record.key();
        OrderPlacedEvent order = record.value();
        System.out.println("Avro message received for key : " + key + " value : " + order.toString());
    }


}

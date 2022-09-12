package com.kaanaydemir.orderservice.kafka.producer;

import com.kaanaydemir.avro.OrderPlacedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class KafkaProducer {
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(String topic,OrderPlacedEvent orderPlacedEvent){
        ListenableFuture<SendResult<String,OrderPlacedEvent>> future=  kafkaTemplate.send(topic,orderPlacedEvent.getOrderName(),orderPlacedEvent);
        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable ex) {
                System.out.println("Message failed to produce");
            }

            @Override
            public void onSuccess(SendResult<String, OrderPlacedEvent> result) {
                System.out.println("Avro message successfully produced");
            }
        });

    }
}

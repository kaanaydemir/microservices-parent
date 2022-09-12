package com.kaanaydemir.orderservice.service;

import com.kaanaydemir.avro.OrderPlacedEvent;
import com.kaanaydemir.orderservice.dto.OrderLineItemDto;
import com.kaanaydemir.orderservice.dto.OrderRequest;
import com.kaanaydemir.orderservice.dto.inventory.InventoryResponse;
import com.kaanaydemir.orderservice.kafka.producer.KafkaProducer;
import com.kaanaydemir.orderservice.model.Order;
import com.kaanaydemir.orderservice.model.OrderLineItem;
import com.kaanaydemir.orderservice.proxy.InventoryServiceProxy;
import com.kaanaydemir.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryServiceProxy inventoryServiceProxy;
    private final KafkaProducer kafkaProducer;

    @Value("${kafka.topic.name}")
    private String topic;

    @Transactional
    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID()
                .toString());
        List<OrderLineItem> orderLineItems = orderRequest.getOrderLineItemDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();
        order.setOrderLineItemList(orderLineItems);

        List<String> skuCodes = order.getOrderLineItemList()
                .stream()
                .map(OrderLineItem::getSkuCode)
                .toList();


        List<InventoryResponse> responses = inventoryServiceProxy.isInStock(skuCodes);

        boolean allIsInStock = responses.stream()
                .allMatch(InventoryResponse::isInStock);

        if (allIsInStock) {
            kafkaProducer.send(topic,new OrderPlacedEvent(order.getOrderNumber()));
            orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("Product is not in stock please try again later");
        }

    }

    private OrderLineItem mapToDto(OrderLineItemDto orderLineItemDto) {
        OrderLineItem orderLineItem = new OrderLineItem();
        orderLineItem.setPrice(orderLineItemDto.getPrice());
        orderLineItem.setQuantity(orderLineItemDto.getQuantity());
        orderLineItem.setSkuCode(orderLineItemDto.getSkuCode());
        return orderLineItem;
    }
}

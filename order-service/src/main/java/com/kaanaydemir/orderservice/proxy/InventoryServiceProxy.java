package com.kaanaydemir.orderservice.proxy;

import com.kaanaydemir.orderservice.dto.inventory.InventoryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@FeignClient(name = "inventory-service")
public interface InventoryServiceProxy {

    @GetMapping("/api/inventory")
    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode);

}

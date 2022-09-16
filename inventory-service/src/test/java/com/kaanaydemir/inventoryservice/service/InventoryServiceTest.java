package com.kaanaydemir.inventoryservice.service;

import com.kaanaydemir.inventoryservice.dto.InventoryResponse;
import com.kaanaydemir.inventoryservice.model.Inventory;
import com.kaanaydemir.inventoryservice.repository.InventoryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class InventoryServiceTest {

    @Mock
    private InventoryRepository inventoryRepository;

    @InjectMocks
    private InventoryService inventoryService;

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void givenListOfInventories_whenCheckWithListOfSkuCodes_thenReturnListOfInventoryResponse() {
        List<Inventory> inventories = List.of(
                Inventory.builder().skuCode("skuCode1").quantity(1).build(),
                Inventory.builder().skuCode("skuCode2").quantity(2).build()
        );

        List<String> skuCodes = List.of("skuCode1", "skuCode2");

        given(inventoryRepository.findBySkuCodeIn(skuCodes)).willReturn(inventories);

        List<InventoryResponse> response = inventoryService.isInStock(skuCodes);
        assertThat(response.size(), is(2));
    }
    @Test
    void givenListOfInventories_whenCheckWithListOfSkuCodesNotContainInDb_thenReturnListOfInventoryResponse() {
        List<String> skuCodes = List.of("skuCode3", "skuCode4");
        List<InventoryResponse> response = inventoryService.isInStock(skuCodes);
        assertThat(response.size(), is(0));
    }
    @Test
    void givenListOfInventories_whenCheckWithListSkuCodesThatEmptyDb_thenReturnListOfInventoryResponse() {
        List<String> skuCodes = List.of("skuCode1", "skuCode2");
        List<InventoryResponse> response = inventoryService.isInStock(skuCodes);
        assertThat(response.size(), is(0));
    }

    @Test
    void givenListOfEmptyInventories_whenCheckWithListOfSkuCodes_thenReturnListOfInventoryResponse() {
        List<Inventory> inventories = List.of();
        List<String> skuCodes = List.of("skuCode1", "skuCode2");
        given(inventoryRepository.findBySkuCodeIn(skuCodes)).willReturn(inventories);
        List<InventoryResponse> response = inventoryService.isInStock(skuCodes);
        assertThat(response.size(), is(0));
    }
}
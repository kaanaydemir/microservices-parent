package com.kaanaydemir.inventoryservice.repository;

import com.kaanaydemir.inventoryservice.model.Inventory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;


@DataJpaTest
class InventoryRepositoryTest {

    @Autowired
    private InventoryRepository inventoryRepository;

    @AfterEach
    void tearDown() {
        inventoryRepository.deleteAll();
    }

    @Test
    void givenListOfSkuCodes_whenFindBySkuCodes_returnListOfInventories() {
        List<String> skuCodes = List.of("skuCode1", "skuCode2");
        List<Inventory> inventories = List.of(
                Inventory.builder().skuCode("skuCode1").quantity(1).build(),
                Inventory.builder().skuCode("skuCode2").quantity(2).build()
        );
        inventoryRepository.saveAll(inventories);
        List<Inventory> response = inventoryRepository.findBySkuCodeIn(skuCodes);
        assertThat(response.size(), is(2));
    }
}
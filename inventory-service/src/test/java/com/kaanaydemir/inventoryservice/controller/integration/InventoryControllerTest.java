package com.kaanaydemir.inventoryservice.controller.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaanaydemir.inventoryservice.model.Inventory;
import com.kaanaydemir.inventoryservice.repository.InventoryRepository;
import com.kaanaydemir.inventoryservice.service.InventoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.CoreMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class InventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void giveListOfSkuCodes_whenCheckIsInStock_thenReturnListOfInventoryResponses() throws Exception {

        inventoryRepository.save(Inventory.builder()
                .skuCode("skuCode")
                .quantity(10)
                .build());

        inventoryRepository.save(Inventory.builder()
                .skuCode("skuCode1")
                .quantity(20)
                .build());


        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("skuCode", "skuCode1");

        ResultActions response = mockMvc.perform(get("/api/inventory/").contentType(MediaType.APPLICATION_JSON)
                .params(requestParams));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(1)));
    }


}

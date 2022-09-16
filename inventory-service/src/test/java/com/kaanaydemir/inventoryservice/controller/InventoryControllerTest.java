package com.kaanaydemir.inventoryservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaanaydemir.inventoryservice.dto.InventoryResponse;
import com.kaanaydemir.inventoryservice.service.InventoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(InventoryController.class)
class InventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InventoryService inventoryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void giveListOfSkuCodes_whenCheckIsInStock_thenReturnListOfInventoryResponses() throws Exception {

        List<InventoryResponse> inventoryResponses = List.of(
                InventoryResponse.builder()
                        .skuCode("skuCode")
                        .isInStock(true)
                        .build(),
                InventoryResponse.builder()
                        .skuCode("skuCode2")
                        .isInStock(true)
                        .build()
        );


        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("skuCode", "skuCode1");


        given(inventoryService.isInStock(anyList())).willReturn(inventoryResponses);

        ResultActions response = mockMvc.perform(get("/api/inventory/").contentType(MediaType.APPLICATION_JSON)
                        .params(requestParams))
                .andExpect(status().isOk());

        response.andDo(print())
                .andExpect(jsonPath("$.size()", is(2)));
    }
}
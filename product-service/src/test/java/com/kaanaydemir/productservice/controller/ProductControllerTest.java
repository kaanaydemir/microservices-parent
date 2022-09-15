package com.kaanaydemir.productservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaanaydemir.productservice.dto.ProductRequest;
import com.kaanaydemir.productservice.dto.ProductResponse;
import com.kaanaydemir.productservice.model.Product;
import com.kaanaydemir.productservice.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void givenProductRequest_whenCreateProduct_thenReturn200() throws Exception {
        ProductRequest productRequest = ProductRequest.builder()
                .description("description")
                .name("name")
                .price(BigDecimal.ONE)
                .build();

        ProductResponse productResponse = ProductResponse.builder()
                .description("description")
                .name("name")
                .price(BigDecimal.ONE)
                .build();

        given(productService.createProduct(productRequest)).willReturn(productResponse);

        String content = objectMapper.writeValueAsString(productRequest);

        ResultActions response = mockMvc.perform(post("/api/product/").contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isCreated());

        response.andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.name", is("name")))
                .andExpect(jsonPath("$.description", is("description")))
                .andExpect(jsonPath("$.price", is(1)));
    }

    @Test
    void givenListOfProducts_whenGetAllProducts_thenReturnProductList() throws Exception {
        List<ProductResponse> productResponseList = List.of(
                ProductResponse.builder()
                        .description("description")
                        .name("name")
                        .price(BigDecimal.ONE)
                        .build(),
                ProductResponse.builder()
                        .description("description")
                        .name("name")
                        .price(BigDecimal.ONE)
                        .build()
        );
        given(productService.getAllProducts()).willReturn(productResponseList);

        ResultActions response = mockMvc.perform(get("/api/product/"));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",is(productResponseList.size())));
    }
}
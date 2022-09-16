package com.kaanaydemir.productservice.controller.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaanaydemir.productservice.dto.ProductRequest;
import com.kaanaydemir.productservice.model.Product;
import com.kaanaydemir.productservice.repository.ProductRepository;
import com.kaanaydemir.productservice.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup(){
        productRepository.deleteAll();
    }

    @Test
    public void givenProductRequest_whenCreateProduct_thenReturnProductResponse() throws Exception {
        ProductRequest productRequest = ProductRequest.builder()
                .name("name")
                .description("description")
                .price(BigDecimal.ONE)
                .build();

        ResultActions response = mockMvc.perform(post("/api/product").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productRequest)));
        response
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(notNullValue())))
                .andExpect(jsonPath("$.name", is(productRequest.getName())))
                .andExpect(jsonPath("$.description", is(productRequest.getDescription())))
                .andExpect(jsonPath("$.price", is(productRequest.getPrice().intValue())));
    }

    @Test
    @DirtiesContext
    public void givenListOfProduct_whenGetAllProducts_thenReturnListOfProductResponse() throws Exception {

        List<Product> products = new ArrayList<>();

        products.add(Product.builder()
                .name("name")
                .description("description")
                .price(BigDecimal.ONE)
                .build());

        products.add(Product.builder()
                .name("name1")
                .description("description1")
                .price(BigDecimal.TEN)
                .build());

        products.add(Product.builder()
                .name("name3")
                .description("description3")
                .price(BigDecimal.ZERO)
                .build());

        productRepository.saveAll(products);

        ResultActions response = mockMvc.perform(get("/api/product"));

        response
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(products.size())));
    }
}

package com.kaanaydemir.productservice.service;

import com.kaanaydemir.productservice.dto.ProductRequest;
import com.kaanaydemir.productservice.model.Product;
import com.kaanaydemir.productservice.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductService productService;

    @Test
    void itCanSaveProduct() {
        ProductRequest productRequest = ProductRequest.builder()
                .description("description")
                .name("name")
                .price(BigDecimal.valueOf(10))
                .build();

        Product product = Product.builder()
                .description("description")
                .name("name")
                .price(BigDecimal.valueOf(10))
                .build();

        productService.createProduct(productRequest);

        verify(productRepository).save(product);
    }

    @Test
    void canGetAllProducts() {
        productService.getAllProducts();

        verify(productRepository).findAll();
    }



}
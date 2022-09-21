package com.kaanaydemir.productservice.service;

import com.kaanaydemir.productservice.dto.ProductRequest;
import com.kaanaydemir.productservice.dto.ProductResponse;
import com.kaanaydemir.productservice.model.Product;
import com.kaanaydemir.productservice.repository.ProductRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.math.BigDecimal;
import org.springframework.core.env.Environment;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.powermock.reflect.Whitebox.invokeMethod;


@RunWith(PowerMockRunner.class)
@PrepareForTest(ProductService.class)
public class ProductServiceTest {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductService productService;

    @Test
    @Order(1)
    public void itCanSaveProduct() {
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
    @Order(2)
    public void canGetAllProducts() {
        productService.getAllProducts();

        verify(productRepository).findAll();
    }

    @Test
    @Order(3)
    public void canMapToProductResponse() throws Exception {
        Product product = Product.builder()
                .description("description")
                .name("name")
                .price(BigDecimal.TEN)
                .build();

        product.setPrice(BigDecimal.valueOf(10));


        ProductResponse response = invokeMethod(productService, "mapToProductResponse", product);
        assertThat(response.getDescription()).isEqualTo("description");
        assertThat(response.getName()).isEqualTo("name");
    }







}
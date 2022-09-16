package com.kaanaydemir.productservice.service;

import com.kaanaydemir.productservice.dto.ProductRequest;
import com.kaanaydemir.productservice.dto.ProductResponse;
import com.kaanaydemir.productservice.model.Product;
import com.kaanaydemir.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest productRequest) {

        Product product = Product.builder ()
                .name (productRequest.getName ())
                .description (productRequest.getDescription ())
                .price (productRequest.getPrice ())
                .build ();

        productRepository.save(product);

        log.info ("Product {} is saved",product.getId ());

        deneme("");

        ProductResponse productResponse = mapToProductResponse (product);

        return productResponse;
    }

    private void deneme(String d) {
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll ();
        return products.stream ().map (this::mapToProductResponse).collect(Collectors.toList());
    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder ()
                .id (product.getId ())
                .name (product.getName ())
                .description (product.getDescription ())
                .price (product.getPrice ())
                .build ();
    }
}

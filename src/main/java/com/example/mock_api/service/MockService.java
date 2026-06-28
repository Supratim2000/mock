package com.example.mock_api.service;

import com.example.mock_api.dto.Product;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Getter
@Service
public class MockService {
    private final ArrayList<Product> productList = new ArrayList<Product>();

    @PostConstruct
    public void init() {
        for(int i = 0; i < 100; i++) {
            Product product = Product.builder()
                    .id("prd" + (i + 1))
                    .sku(String.valueOf(1000 + i))
                    .name("Product" + i)
                    .price(500 * (i + 1))
                    .build();
            productList.add(product);
        }
    }

    public void modifyResponse(Product product) {
        for (int i = 0; i < productList.size(); i++) {
            if (productList.get(i).getId().equals(product.getId())) {
                productList.set(i, product);
                return;
            }
        }

        productList.add(product);
    }
}

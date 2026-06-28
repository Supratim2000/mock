package com.example.mock_api.dto;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    private String id;
    private String sku;
    private String name;
    private int price;
}

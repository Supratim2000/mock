package com.example.mock_api.controller;

import com.example.mock_api.dto.Product;
import com.example.mock_api.service.MockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1")
public class MockController {
    @Autowired
    private MockService mockService;

    @GetMapping("/mock-response")
    public ResponseEntity<ArrayList<Product>> getMockResponse() {
        return ResponseEntity.ok(mockService.getProductList());
    }

    @PostMapping("/modify-response")
    public ResponseEntity<ArrayList<Product>> modifyMockResponse(@RequestBody Product product) {
        mockService.modifyResponse(product);
        return ResponseEntity.ok(mockService.getProductList());
    }
}

package com.andro.microservices.order_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record OrderRequest(Long id, String orderNumber, @JsonProperty("skucode") String skuCode, BigDecimal price, Integer quantity, UserDetails userDetails) {

    public record UserDetails(String email, String firstName, String lastName) {}
}

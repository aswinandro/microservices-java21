package com.andro.microservices.order_service.service;

import com.andro.microservices.order_service.client.InventoryClient;
import com.andro.microservices.order_service.dto.OrderRequest;
import com.andro.microservices.order_service.model.Order;
import com.andro.microservices.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;
    public void placeOrder(OrderRequest orderRequest) {
        var isProductInStock = inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());
//        map OrderRequest to Order Object
        if (!isProductInStock) {
            Order order = new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            order.setPrice(orderRequest.price());
            order.setSkuCode(orderRequest.skuCode());
            order.setQuantity(orderRequest.quantity());
            orderRepository.save(order);
        }
        else{
            throw new RuntimeException("Product" + orderRequest.skuCode() + "is not in stock, please try again later");
        }
    }

}

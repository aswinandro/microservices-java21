package com.andro.inventory_service.controller;


import com.andro.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor

public class InventoryController {
    private final InventoryService inventoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(@RequestParam String skucode, @RequestParam Integer quantity){
        return inventoryService.isInStock(skucode, quantity);
    }
}

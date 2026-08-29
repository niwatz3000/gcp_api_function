package com.coins.controller;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/coins")
@CrossOrigin(origins = "*")
public class CoinController {

    @GetMapping
    public List<Map<String, Object>> getCoins(@RequestParam(required = false) String search) {
        return List.of(
            Map.of("id", 1, "code", "GOLD-001", "name", "Gold Eagle 1oz", "category", "Gold", "price", 2500.00, "stock", 10),
            Map.of("id", 2, "code", "SILV-001", "name", "Silver Maple Leaf 1oz", "category", "Silver", "price", 35.50, "stock", 50)
        );
    }

    @PostMapping
    public Map<String, String> createCoin(@RequestBody Map<String, Object> coin) {
        return Map.of("message", "Coin created successfully", "status", "success");
    }

    @PutMapping("/{id}")
    public Map<String, String> updateCoin(@PathVariable Long id, @RequestBody Map<String, Object> coin) {
        return Map.of("message", "Coin updated successfully", "status", "success");
    }

    @DeleteMapping("/{id}")
    public Map<String, String> deleteCoin(@PathVariable Long id) {
        return Map.of("message", "Coin deleted successfully", "status", "success");
    }

    @GetMapping("/export")
    public String exportCsv() {
        return "ID,Code,Name,Category,Price,Stock\n1,GOLD-001,Gold Eagle 1oz,Gold,2500.00,10\n2,SILV-001,Silver Maple Leaf 1oz,Silver,35.50,50";
    }
}

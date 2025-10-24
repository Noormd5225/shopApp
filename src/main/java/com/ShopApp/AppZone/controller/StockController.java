package com.ShopApp.AppZone.controller;


import com.ShopApp.AppZone.model.Stock;
import com.ShopApp.AppZone.model.VerifiedItem;
import com.ShopApp.AppZone.repository.StockRepository;
import com.ShopApp.AppZone.repository.VerifiedItemRepository;
import com.ShopApp.AppZone.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import org.springframework.web.bind.annotation.*;

import java.util.List;




import com.ShopApp.AppZone.model.Stock;
import com.ShopApp.AppZone.service.StockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/stocks")
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    // ✅ Insert or update multiple stock items
    @PostMapping("/insert-or-update")
    public ResponseEntity<String> insertOrUpdateStock(@RequestBody List<Stock> stockList) {
        if (stockList == null || stockList.isEmpty()) {
            return ResponseEntity.badRequest().body("Stock list is empty");
        }

        stockList.forEach(stock -> {
            stockService.addOrUpdateStock(stock.getItemClassificationId(), stock.getQuantity());
        });

        return ResponseEntity.ok("Stock updated successfully");
    }

    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private VerifiedItemRepository verifiedItemRepository;

    // ✅ New API: Stock ID, Item Classification, Verification Quantity
    @GetMapping("/verification-quantity")
    public ResponseEntity<List<Map<String, Object>>> getStockWithVerificationQuantity() {
        List<Stock> stocks = stockRepository.findAll();

        // Group verification quantity by itemClassificationId
        Map<String, Integer> verificationQuantityMap = verifiedItemRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        VerifiedItem::getItemClassificationId,
                        Collectors.summingInt(VerifiedItem::getQuantity)
                ));

        List<Map<String, Object>> result = new ArrayList<>();

        for (Stock stock : stocks) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("stockId", stock.getStockId());
            map.put("itemClassificationId", stock.getItemClassificationId());
            map.put("verificationQuantity", verificationQuantityMap.getOrDefault(stock.getItemClassificationId(), 0));
            result.add(map);
        }

        if (result.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(result);
    }




    // Get all stocks
    @GetMapping("/list")
    public ResponseEntity<List<Stock>> getAllStocks() {
        return ResponseEntity.ok(stockService.getAllStocks());
    }
}

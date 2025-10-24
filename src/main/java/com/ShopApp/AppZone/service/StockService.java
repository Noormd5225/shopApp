package com.ShopApp.AppZone.service;


import com.ShopApp.AppZone.model.PurchaseItem;
import com.ShopApp.AppZone.model.Stock;
import com.ShopApp.AppZone.repository.StockRepository;
import org.springframework.stereotype.Service;
import java.util.List;



import com.ShopApp.AppZone.model.Stock;
import com.ShopApp.AppZone.repository.StockRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockService {

    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    // Insert or update stock
    public Stock addOrUpdateStock(String itemClassificationId, int quantity) {
        return stockRepository.findByItemClassificationId(itemClassificationId)
                .map(existingStock -> {
                    existingStock.setQuantity(existingStock.getQuantity() + quantity);
                    return stockRepository.save(existingStock);
                })
                .orElseGet(() -> stockRepository.save(new Stock(itemClassificationId, quantity)));
    }

    // Get all stocks
    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    // Optional: delete all (for testing)
    public void deleteAll() {
        stockRepository.deleteAll();
    }
}

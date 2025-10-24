package com.ShopApp.AppZone.controller;



import com.ShopApp.AppZone.model.SalesItem;
import com.ShopApp.AppZone.repository.SalesItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/sale-filter")
public class SaleFilterController {

    @Autowired
    private SalesItemRepository salesItemRepository;

    // Get all sales items with required fields
    @GetMapping("/all")
    public List<SalesItemDTO> getAllSalesItems() {
        return salesItemRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Filter by salesId
    @GetMapping("/salesid")
    public List<SalesItemDTO> getBySalesId(@RequestParam String salesId) {
        return salesItemRepository.findAll().stream()
                .filter(item -> item.getSales() != null && salesId.equals(item.getSales().getSalesId()))
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Filter by particulars (partial match)
    @GetMapping("/particulars")
    public List<SalesItemDTO> getByParticulars(@RequestParam String particulars) {
        return salesItemRepository.findAll().stream()
                .filter(item -> item.getParticulars() != null &&
                        item.getParticulars().toLowerCase().contains(particulars.toLowerCase()))
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // DTO to return only required fields
    private SalesItemDTO toDTO(SalesItem item) {
        return new SalesItemDTO(
                item.getSales() != null ? item.getSales().getSalesId() : null,
                item.getParticulars(),
                item.getQuantity(),
                item.getTotal()
        );
    }

    // DTO class
    public static class SalesItemDTO {
        private String salesId;
        private String particulars;
        private Integer quantity;
        private java.math.BigDecimal total;

        public SalesItemDTO(String salesId, String particulars, Integer quantity, java.math.BigDecimal total) {
            this.salesId = salesId;
            this.particulars = particulars;
            this.quantity = quantity;
            this.total = total;
        }

        public String getSalesId() { return salesId; }
        public String getParticulars() { return particulars; }
        public Integer getQuantity() { return quantity; }
        public java.math.BigDecimal getTotal() { return total; }
    }
}

